package ru.bigint.webapp.repository.postgis;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bigint.webapp.dto.ColoredPolygon;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class HexagonalGrid {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public HexagonalGrid(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<String> getHexagonalGrid(double hexagonRadius) {
        String str = """
                SELECT ST_AsText(geom)
                FROM ST_HexagonGrid(
                	:radius,
                	ST_Transform(ST_SetSRID(ST_MakeBox2D(ST_Point(37.34, 55.55), ST_Point(37.88, 55.93)), 4326), 3857)
                )
                """;
        return namedParameterJdbcTemplate.queryForList(str, Map.of("radius", hexagonRadius), String.class);
    }

    @Transactional
    public List<ColoredPolygon> getHousesSumAreasPerHexagon(double hexagonRadius) {
        namedParameterJdbcTemplate.update("""
                    CREATE TEMP TABLE hexgrid_temp (geom geography, i integer, j integer) ON COMMIT DROP;
                    CREATE TEMP TABLE houses_temp (geom geography, area double precision) ON COMMIT DROP;
                    INSERT INTO houses_temp
                    SELECT ST_SetSRID(ST_MakePoint(longitude, latitude), 4326), square
                    FROM houses;
                    INSERT INTO hexgrid_temp
                    SELECT ST_Transform(ST_SetSRID(geom, 3857), 4326), i, j
                    FROM ST_HexagonGrid(
                        :radius,
                        ST_Transform( ST_SetSRID((select ST_Extent(geom::geometry) from houses_temp), 4326), 3857 )
                    );
                    CREATE INDEX hexgrid_temp_geom_index ON hexgrid_temp USING GIST(geom);
                    CREATE INDEX houses_temp_geom_index ON houses_temp USING GIST(geom);
                """, Map.of("radius", hexagonRadius));

        String query = """
                    SELECT ST_AsText(ST_Transform(ST_SetSRID(hexgrid_temp.geom::geometry, 4326), 3857)) as wkt, SUM(area) as area
                    FROM hexgrid_temp
                    INNER JOIN houses_temp ON ST_CoveredBy(houses_temp.geom, hexgrid_temp.geom)
                    GROUP BY hexgrid_temp.geom;
                """;
        List<ColoredPolygon> result = namedParameterJdbcTemplate.queryForStream(query, Map.of("radius", hexagonRadius), (rs, row) -> {
            ColoredPolygon p = new ColoredPolygon();
            p.setWkt(rs.getString("wkt"));
            p.setWeight(rs.getDouble("area"));
            return p;
        }).toList();
//        double max = 75000;
//        double avg = result.stream().mapToDouble(ColoredPolygon::getWeight).sum() / result.size();
//        double max = result.stream().mapToDouble(ColoredPolygon::getWeight).max().orElse(1);
        double middle = result.stream().sorted(Comparator.comparing(ColoredPolygon::getWeight)).limit(result.size() / 2)
                .mapToDouble(ColoredPolygon::getWeight).max().orElse(1) * 3;
        for (ColoredPolygon p : result) {
            double weight = p.getWeight() / middle;
            if (weight > 1d) weight = 1;
            p.setWeight(weight);
//            p.setWeight(p.getWeight());
//            double weight = p.getWeight() / max;
//            p.setWeight(weight > 1 ? 1 : weight);
        }
        return result;
    }
}

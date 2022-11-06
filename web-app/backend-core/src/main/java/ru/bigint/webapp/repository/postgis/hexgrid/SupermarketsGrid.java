package ru.bigint.webapp.repository.postgis.hexgrid;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bigint.webapp.dto.ColoredPolygon;
import ru.bigint.webapp.dto.HexagonEntry;

import java.util.List;
import java.util.Map;

@Repository
public class SupermarketsGrid {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public SupermarketsGrid(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional
    public List<HexagonEntry> getGrid(double hexagonRadius) {
        namedParameterJdbcTemplate.update("""
                    CREATE TEMP TABLE hexgrid_temp (geom geography, i integer, j integer) ON COMMIT DROP;
                    CREATE TEMP TABLE supermarkets_temp (geom geography, id int) ON COMMIT DROP;
                    INSERT INTO supermarkets_temp
                    SELECT ST_SetSRID(ST_MakePoint(longitude, latitude), 4326), MIN(id)
                    FROM supermarkets WHERE latitude is not null and longitude is not null
                    GROUP BY name, address, about, latitude, longitude;
                    INSERT INTO hexgrid_temp
                    SELECT ST_Transform(ST_SetSRID(geom, 3857), 4326), i, j
                    FROM ST_HexagonGrid(
                        :radius::float,
                        ST_Transform( ST_SetSRID((select ST_Extent(geom::geometry) from supermarkets_temp), 4326), 3857 )
                    );
                    CREATE INDEX hexgrid_temp_geom_index ON hexgrid_temp USING GIST(geom);
                    CREATE INDEX supermarkets_temp_geom_index ON supermarkets_temp USING GIST(geom);
                """, Map.of("radius", hexagonRadius));

        String query = """
                    SELECT MIN(ST_AsText(ST_Transform(ST_SetSRID(hexgrid_temp.geom::geometry, 4326), 3857))) as wkt, COUNT(id) as cnt, hexgrid_temp.i, hexgrid_temp.j
                    FROM hexgrid_temp
                    INNER JOIN supermarkets_temp ON ST_CoveredBy(supermarkets_temp.geom, hexgrid_temp.geom)
                    GROUP BY hexgrid_temp.i, hexgrid_temp.j;
                """;
        return namedParameterJdbcTemplate.queryForStream(query, Map.of("radius", hexagonRadius), (rs, row) -> {
            ColoredPolygon p = new ColoredPolygon();
            p.setWkt(rs.getString("wkt"));
            p.setWeight(rs.getInt("cnt"));
            HexagonEntry e = new HexagonEntry();
            e.setIndex(rs.getString("i") + "_" + rs.getString("j"));
            e.setColoredPolygon(p);
            return e;
        }).toList();
    }
}

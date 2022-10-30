package ru.bigint.webapp.repository.postgis;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class HexagonalGrid {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public HexagonalGrid(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<String> getHexagonalGrid(double radius) {
        String str = """
                SELECT ST_AsText(geom)
                FROM ST_HexagonGrid(
                	:radius,
                	ST_Transform( ST_SetSRID((select ST_Extent(lonlat::geometry) from houses_geography), 4326), 3857 )
                ) hex1
                INNER JOIN houses_geography hg ON ST_Intersects(hg.lonlat, ST_Transform(hex1.geom, 4326))
                GROUP BY geom
                """;
        return namedParameterJdbcTemplate.queryForList(str, Map.of("radius", radius), String.class);
    }
}

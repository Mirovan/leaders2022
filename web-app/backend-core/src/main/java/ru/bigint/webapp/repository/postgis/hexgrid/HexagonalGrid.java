package ru.bigint.webapp.repository.postgis.hexgrid;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bigint.webapp.dto.ColoredPolygon;

import java.util.Comparator;
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
}

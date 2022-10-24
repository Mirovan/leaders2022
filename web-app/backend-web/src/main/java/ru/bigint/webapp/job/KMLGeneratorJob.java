package ru.bigint.webapp.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.bigint.webapp.dto.KmlJobParams;
import ru.bigint.webapp.entity.HeatmapPoint;
import ru.bigint.webapp.entity.KmlJob;
import ru.bigint.webapp.repository.KmlJobRepo;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class KMLGeneratorJob {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final KmlJobRepo jobRepo;

    public KMLGeneratorJob(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, KmlJobRepo jobRepo) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jobRepo = jobRepo;
    }

    @Scheduled(fixedDelay = 5000)
    public void scheduleJob() {
        Optional<KmlJob> jobOptional = jobRepo.findFirstByStatusOrderByCreatedAtDesc(0);
        jobOptional.ifPresent(job -> {
            LOGGER.info("Starting KML Job {} at {}", job.getId(), Instant.now());
            job.setStatus(1);
            job.setCompletedAt(null);
            job.setResultKml(null);
            jobRepo.save(job);
            try {
                byte[] result = runJob(job);
                job.setResultKml(result);
                job.setCompletedAt(Timestamp.from(Instant.now()));
                job.setStatus(2);
                jobRepo.save(job);
                LOGGER.info("Finished KML Job {} at {}", job.getId(), Instant.now());
            } catch (Exception e) {
                job.setStatus(3);
                jobRepo.save(job);
                LOGGER.info("Finished with ERROR KML Job {} at {}", job.getId(), Instant.now());
            }
        });
    }

    private byte[] runJob(KmlJob job) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        KmlJobParams params = objectMapper.readValue(job.getParams(), KmlJobParams.class);
        jdbcTemplate.execute("REFRESH MATERIALIZED VIEW houses_geography");
        List<HeatmapPoint> points = namedParameterJdbcTemplate.queryForStream("""
                        SELECT h1.id, h1.address, ST_X(h1.lonlat::geometry) lon, ST_Y(h1.lonlat::geometry) lat,
                               SUM(h2.square) as value
                        FROM houses_geography h1
                        LEFT JOIN houses_geography h2 ON ST_DWithin(h1.lonlat, h2.lonlat, :distance)
                        GROUP BY h1.id, h1.address, h1.lonlat
                          """,
                Map.of("distance", params.getDistance()),
                (rs, rowNum) -> {
                    HeatmapPoint heatmapPoint = new HeatmapPoint();
                    heatmapPoint.setId(rs.getString("id"));
                    heatmapPoint.setAddress(rs.getString("address"));
                    heatmapPoint.setLat(rs.getDouble("lat"));
                    heatmapPoint.setLon(rs.getDouble("lon"));
                    heatmapPoint.setValue(rs.getDouble("value"));
                    return heatmapPoint;
                }).toList();
        double max = points.stream().max(Comparator.comparing(HeatmapPoint::getValue)).map(HeatmapPoint::getValue).orElse(1d);
        StringBuilder sb = new StringBuilder();
        for (HeatmapPoint point : points) {
            double value = point.getValue() / max;
            sb.append(String.format(Locale.US, "%s %.2f %.6f %.6f", point.getId(), value, point.getLon(), point.getLat()) + System.lineSeparator());
        }
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

}

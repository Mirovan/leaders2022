package ru.bigint.webapp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.bigint.webapp.dto.KmlJobParams;
import ru.bigint.webapp.entity.KmlJob;
import ru.bigint.webapp.repository.KmlJobRepo;
import ru.bigint.webapp.service.iface.KmlJobService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class KmlJobServiceImpl implements KmlJobService {

    private final KmlJobRepo kmlJobRepo;

    public KmlJobServiceImpl(KmlJobRepo kmlJobRepo) {
        this.kmlJobRepo = kmlJobRepo;
    }

    @Override
    public byte[] getKmlResultById(String id) {
        return kmlJobRepo.findById(id)
                .map(KmlJob::getResultKml)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<KmlJob> listAllOrderedByDate() {
        return kmlJobRepo.listAllOrderedByDate();
    }

    @Override
    public void createJob(KmlJobParams params) {
        ObjectMapper objectMapper = new ObjectMapper();
        KmlJob job = new KmlJob();
        job.setId(UUID.randomUUID().toString());
        job.setCreatedAt(Timestamp.from(Instant.now()));
        try {
            job.setParams(objectMapper.writeValueAsString(params));
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        job.setStatus(0);
        kmlJobRepo.save(job);
    }
}

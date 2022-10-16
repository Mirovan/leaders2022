package ru.bigint.webapp.service.iface;

import ru.bigint.webapp.dto.KmlJobParams;
import ru.bigint.webapp.entity.KmlJob;

import java.util.List;

public interface KmlJobService {

    byte[] getKmlResultById(String id);

    List<KmlJob> listAllOrderedByDate();

    void createJob(KmlJobParams params);
}

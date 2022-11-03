package ru.bigint.webapp.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.MfcDto;
import ru.bigint.webapp.repository.MfcRepo;
import ru.bigint.webapp.service.iface.MfcService;

import java.util.List;

@Service
public class MfcServiceImpl implements MfcService {
    private final MfcRepo mfcRepo;

    public MfcServiceImpl(MfcRepo mfcRepo) {
        this.mfcRepo = mfcRepo;
    }

    @Override
    public List<MfcDto> findNearest(Coord point, int count) {
        return mfcRepo.findNearest(point, Pageable.ofSize(count));
    }
}

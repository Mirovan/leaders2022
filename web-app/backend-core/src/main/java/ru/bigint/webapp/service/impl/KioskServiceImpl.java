package ru.bigint.webapp.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.KioskDto;
import ru.bigint.webapp.repository.KioskRepo;
import ru.bigint.webapp.service.iface.KioskService;

import java.util.List;

@Service
public class KioskServiceImpl implements KioskService {
    private final KioskRepo kioskRepo;

    public KioskServiceImpl(KioskRepo kioskRepo) {
        this.kioskRepo = kioskRepo;
    }

    @Override
    public List<KioskDto> findNearest(Coord point, int count) {
        return kioskRepo.findNearest(point, Pageable.ofSize(count));
    }
}

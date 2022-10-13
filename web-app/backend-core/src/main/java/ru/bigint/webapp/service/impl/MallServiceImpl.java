package ru.bigint.webapp.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.MallDto;
import ru.bigint.webapp.entity.Mall;
import ru.bigint.webapp.repository.MallRepo;
import ru.bigint.webapp.service.iface.MallService;

import java.util.List;

@Service
public class MallServiceImpl implements MallService {
    private final MallRepo mallRepo;

    public MallServiceImpl(MallRepo mallRepo) {
        this.mallRepo = mallRepo;
    }

    @Override
    public List<MallDto> findNearest(Coord point, int count) {
        return mallRepo.findNearest(point, Pageable.ofSize(count));
    }
}

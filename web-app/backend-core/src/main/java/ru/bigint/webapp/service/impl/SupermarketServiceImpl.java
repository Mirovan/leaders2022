package ru.bigint.webapp.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.MallDto;
import ru.bigint.webapp.dto.SupermarketDto;
import ru.bigint.webapp.repository.SupermarketRepo;
import ru.bigint.webapp.service.iface.MallService;
import ru.bigint.webapp.service.iface.SupermarketService;

import java.util.List;

@Service
public class SupermarketServiceImpl implements SupermarketService {
    private final SupermarketRepo supermarketRepo;

    public SupermarketServiceImpl(SupermarketRepo supermarketRepo) {
        this.supermarketRepo = supermarketRepo;
    }

    @Override
    public List<SupermarketDto> findNearest(Coord point, int count) {
        return supermarketRepo.findNearest(point, Pageable.ofSize(count));
    }
}

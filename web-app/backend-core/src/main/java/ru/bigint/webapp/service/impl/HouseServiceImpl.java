package ru.bigint.webapp.service.impl;

import org.springframework.stereotype.Service;
import ru.bigint.webapp.entity.House;
import ru.bigint.webapp.repository.HouseRepo;
import ru.bigint.webapp.service.iface.HouseService;

import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {
    private final HouseRepo houseRepo;

    public HouseServiceImpl(HouseRepo houseRepo) {
        this.houseRepo = houseRepo;
    }

    @Override
    public House getById(Integer id) {
        return null;
    }

    @Override
    public House add(House channel) {
        return null;
    }

    @Override
    public House update(House channel) {
        return null;
    }

    @Override
    public List<House> getAll() {
        return houseRepo.findAll();
    }
}

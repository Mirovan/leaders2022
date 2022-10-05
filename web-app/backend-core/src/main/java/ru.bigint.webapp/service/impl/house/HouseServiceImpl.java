package ru.bigint.webapp.service.impl.house;

import org.springframework.stereotype.Service;
import ru.bigint.webapp.data.dao.house.HouseRepo;
import ru.bigint.webapp.data.entity.House;
import ru.bigint.webapp.service.iface.house.HouseService;

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

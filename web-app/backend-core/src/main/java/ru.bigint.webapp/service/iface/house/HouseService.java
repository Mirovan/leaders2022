package ru.bigint.webapp.service.iface.house;

import ru.bigint.webapp.data.entity.House;

import java.util.List;

public interface HouseService {
    House getById(Integer id);

    House add(House channel);

    House update(House channel);

    List<House> getAll();
}

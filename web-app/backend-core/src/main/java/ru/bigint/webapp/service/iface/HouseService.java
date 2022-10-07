package ru.bigint.webapp.service.iface;

import ru.bigint.webapp.entity.House;

import java.util.List;

public interface HouseService {
    House getById(Integer id);

    House add(House channel);

    House update(House channel);

    List<House> getAll();
}

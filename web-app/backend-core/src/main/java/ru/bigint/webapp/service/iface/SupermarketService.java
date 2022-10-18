package ru.bigint.webapp.service.iface;

import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.SupermarketDto;

import java.util.List;

public interface SupermarketService {
    List<SupermarketDto> findNearest(Coord point, int count);
}

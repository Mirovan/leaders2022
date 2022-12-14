package ru.bigint.webapp.service.iface;

import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.MallDto;

import java.util.List;

public interface MallService {
    List<MallDto> findNearest(Coord point, int count);
}

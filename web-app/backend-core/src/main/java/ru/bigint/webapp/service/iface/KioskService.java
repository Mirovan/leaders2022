package ru.bigint.webapp.service.iface;

import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.KioskDto;

import java.util.List;

public interface KioskService {
    List<KioskDto> findNearest(Coord point, int count);
}

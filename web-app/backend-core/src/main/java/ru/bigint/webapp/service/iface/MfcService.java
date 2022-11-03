package ru.bigint.webapp.service.iface;

import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.MfcDto;

import java.util.List;

public interface MfcService {
    List<MfcDto> findNearest(Coord point, int count);
}

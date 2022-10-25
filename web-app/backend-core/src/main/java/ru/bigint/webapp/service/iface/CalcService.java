package ru.bigint.webapp.service.iface;

import ru.bigint.webapp.dto.CalcPointDto;

import java.util.List;

public interface CalcService {
    List<CalcPointDto> calculate(int radius, String kmlId);
}

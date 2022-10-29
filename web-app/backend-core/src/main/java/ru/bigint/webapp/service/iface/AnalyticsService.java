package ru.bigint.webapp.service.iface;

import ru.bigint.webapp.dto.AnalyticsDto;

import java.util.List;

public interface AnalyticsService {
    List<AnalyticsDto> getUsingStat();
}

package ru.bigint.webapp.service.impl;

import org.springframework.stereotype.Service;
import ru.bigint.webapp.dto.AnalyticsDto;
import ru.bigint.webapp.dto.CalcPointDto;
import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.service.iface.AnalyticsService;
import ru.bigint.webapp.service.iface.CalcService;
import ru.bigint.webapp.service.iface.HouseService;
import ru.bigint.webapp.service.iface.KmlJobService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    @Override
    public List<AnalyticsDto> getUsingStat() {
        List<AnalyticsDto> res = new ArrayList<>();
        res.add(new AnalyticsDto("Недозагруженные постаматы", 20, "green"));
        res.add(new AnalyticsDto("Перегруженные постаматы", 10, "red"));
        res.add(new AnalyticsDto("Остальные постаматы", 70, "gray"));
        return res;
    }
}

package ru.bigint.webapp.service.impl;

import org.springframework.stereotype.Service;
import ru.bigint.webapp.dto.ColoredPolygon;
import ru.bigint.webapp.repository.postgis.HexagonalGrid;
import ru.bigint.webapp.service.iface.CalcService;
import ru.bigint.webapp.service.iface.HouseService;
import ru.bigint.webapp.service.iface.KmlJobService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalcServiceImpl implements CalcService {

    private final KmlJobService kmlJobService;
    private final HouseService houseService;

    private final HexagonalGrid hexagonalGrid;

    public CalcServiceImpl(KmlJobService kmlJobService, HouseService houseService, HexagonalGrid hexagonalGrid) {
        this.kmlJobService = kmlJobService;
        this.houseService = houseService;
        this.hexagonalGrid = hexagonalGrid;
    }

    @Override
    public List<ColoredPolygon> calculate(int radius, String kmlId, boolean considerMalls, boolean considerSupermarkets,
                                          boolean considerMetro, boolean considerPostamat) {
        List<ColoredPolygon> res = new ArrayList<>();

        res.addAll(hexagonalGrid.getHousesSumAreasPerHexagon(radius));

        return res;
    }
}

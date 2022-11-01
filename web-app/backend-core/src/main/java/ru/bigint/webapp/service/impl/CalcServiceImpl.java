package ru.bigint.webapp.service.impl;

import org.springframework.stereotype.Service;
import ru.bigint.webapp.dto.ColoredPolygon;
import ru.bigint.webapp.dto.HexagonEntry;
import ru.bigint.webapp.repository.postgis.hexgrid.*;
import ru.bigint.webapp.service.iface.CalcService;
import ru.bigint.webapp.service.iface.HouseService;
import ru.bigint.webapp.service.iface.KmlJobService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalcServiceImpl implements CalcService {

    private final KmlJobService kmlJobService;
    private final HouseService houseService;

    private final HexagonalGrid hexagonalGrid;

    private final HousesGrid housesGrid;

    private final MallsGrid mallsGrid;

    private final SupermarketsGrid supermarketsGrid;

    private final MetroGrid metroGrid;

    public CalcServiceImpl(KmlJobService kmlJobService, HouseService houseService, HexagonalGrid hexagonalGrid,
                           HousesGrid housesGrid, MallsGrid mallsGrid, SupermarketsGrid supermarketsGrid,
                           MetroGrid metroGrid) {
        this.kmlJobService = kmlJobService;
        this.houseService = houseService;
        this.hexagonalGrid = hexagonalGrid;
        this.housesGrid = housesGrid;
        this.mallsGrid = mallsGrid;
        this.supermarketsGrid = supermarketsGrid;
        this.metroGrid = metroGrid;
    }

    @Override
    public List<ColoredPolygon> calculate(int radius, String kmlId, boolean considerHouses, boolean considerMalls,
                                          boolean considerSupermarkets, boolean considerMetro,
                                          boolean considerPostamat, boolean considerWorkCenter,
                                          boolean considerChildHouse, boolean considerParking) {
        Map<String, ColoredPolygon> grid = new HashMap<>();
        if (considerHouses) {
            putToGrid(grid, housesGrid.getGrid(radius));
        }
        if (considerMalls) {
            putToGrid(grid, mallsGrid.getGrid(radius));
        }
        if (considerSupermarkets) {
            putToGrid(grid, supermarketsGrid.getGrid(radius));
        }
        if (considerMetro) {
            putToGrid(grid, metroGrid.getGrid(radius));
        }
        normalizeWeights(grid);
        return new ArrayList<>(grid.values());
    }

    private void normalizeWeights(Map<String, ColoredPolygon> grid) {
        double max = grid.values().stream().mapToDouble(ColoredPolygon::getWeight).max().orElse(1);
        for (ColoredPolygon p : grid.values()) {
            p.setWeight(p.getWeight() / max);
        }
    }

    private void putToGrid(Map<String, ColoredPolygon> hexagon, List<HexagonEntry> entries) {
        for (HexagonEntry he : entries) {
            if (hexagon.containsKey(he.getIndex())) {
                double sum = hexagon.get(he.getIndex()).getWeight() + he.getColoredPolygon().getWeight();
                he.getColoredPolygon().setWeight(sum);
            }
            hexagon.put(he.getIndex(), he.getColoredPolygon());
        }
    }
}

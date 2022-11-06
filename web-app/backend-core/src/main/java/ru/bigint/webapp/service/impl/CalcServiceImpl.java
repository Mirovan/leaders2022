package ru.bigint.webapp.service.impl;

import org.springframework.stereotype.Service;
import ru.bigint.webapp.dto.ColoredPolygon;
import ru.bigint.webapp.dto.HexagonEntry;
import ru.bigint.webapp.repository.postgis.hexgrid.*;
import ru.bigint.webapp.service.iface.CalcService;
import ru.bigint.webapp.service.iface.HouseService;
import ru.bigint.webapp.service.iface.KmlJobService;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        if (considerHouses) {
            threadPool.submit(() -> putToGrid(grid, multiply(housesGrid.getGrid(radius), 10)));
        }
        if (considerMalls) {
            threadPool.submit(() -> putToGrid(grid, multiply(recalcWeightByPosition(mallsGrid.getGrid(radius)), 2)));
        }
        if (considerSupermarkets) {
            threadPool.submit(() -> putToGrid(grid, multiply(recalcWeightByPosition(supermarketsGrid.getGrid(radius)), 2)));
        }
        if (considerMetro) {
            threadPool.submit(() -> putToGrid(grid, multiply(recalcWeightByPosition(metroGrid.getGrid(radius)), 4)));
        }
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        normalizeWeights(grid);
        return new ArrayList<>(grid.values());
    }

    private List<HexagonEntry> multiply(List<HexagonEntry> entries, double multiplier) {
        for (HexagonEntry entry : entries) {
            entry.getColoredPolygon().setWeight(entry.getColoredPolygon().getWeight() * multiplier);
        }
        return entries;
    }

    private void normalizeWeights(Map<String, ColoredPolygon> grid) {
        double max = grid.values().stream().mapToDouble(ColoredPolygon::getWeight).max().orElse(1);
        for (ColoredPolygon p : grid.values()) {
            p.setWeight(p.getWeight() / max);
        }
    }

    private List<HexagonEntry> recalcWeightByPosition(List<HexagonEntry> entries) {
        List<Double> weights = entries.stream().map(HexagonEntry::getColoredPolygon).map(ColoredPolygon::getWeight)
                .distinct().sorted().toList();
        Map<Double, Double> weightToRecalculated = new HashMap<>();
        int count = weights.size();
        double step = 1d / (count - 1);
        double newWeight = 0;
        for (Double weight : weights) {
            weightToRecalculated.put(weight, newWeight);
            newWeight += step;
        }
        for (HexagonEntry hexagonEntry : entries) {
            ColoredPolygon coloredPolygon = hexagonEntry.getColoredPolygon();
            coloredPolygon.setWeight(weightToRecalculated.get(coloredPolygon.getWeight()));
        }
        return entries;
    }

    private void putToGrid(Map<String, ColoredPolygon> hexagon, List<HexagonEntry> entries) {
        double max = entries.stream().map(HexagonEntry::getColoredPolygon).mapToDouble(ColoredPolygon::getWeight).max().orElse(1);
        for (HexagonEntry p : entries) {
            p.getColoredPolygon().setWeight(p.getColoredPolygon().getWeight() / max);
        }
        synchronized (hexagon) {
            for (HexagonEntry he : entries) {
                if (hexagon.containsKey(he.getIndex())) {
                    double sum = hexagon.get(he.getIndex()).getWeight() + he.getColoredPolygon().getWeight();
                    he.getColoredPolygon().setWeight(sum);
                }
                hexagon.put(he.getIndex(), he.getColoredPolygon());
            }
        }
    }
}

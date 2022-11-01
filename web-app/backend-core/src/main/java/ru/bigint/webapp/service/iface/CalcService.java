package ru.bigint.webapp.service.iface;

import ru.bigint.webapp.dto.ColoredPolygon;

import java.util.List;

public interface CalcService {
    List<ColoredPolygon> calculate(int radius, String kmlId, boolean considerHouses, boolean considerMalls, boolean considerSupermarkets, boolean considerMetro, boolean considerPostamat, boolean considerWorkCenter, boolean considerChildHouse, boolean considerParking);
}

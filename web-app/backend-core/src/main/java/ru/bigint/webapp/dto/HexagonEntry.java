package ru.bigint.webapp.dto;

public class HexagonEntry {

    private String index;

    private ColoredPolygon coloredPolygon;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public ColoredPolygon getColoredPolygon() {
        return coloredPolygon;
    }

    public void setColoredPolygon(ColoredPolygon coloredPolygon) {
        this.coloredPolygon = coloredPolygon;
    }
}

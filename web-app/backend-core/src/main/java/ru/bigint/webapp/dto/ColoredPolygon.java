package ru.bigint.webapp.dto;

public class ColoredPolygon {
    // Описание полигона
    private String wkt;
    // Весовой коэфициент = 0..1
    private double weight;

    public ColoredPolygon() {
    }

    public String getWkt() {
        return wkt;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}

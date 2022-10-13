package ru.bigint.webapp.dto;

public class Coord {
    private Double lalitude;
    private Double longitude;

    public Coord(Double lalitude, Double longitude) {
        this.lalitude = lalitude;
        this.longitude = longitude;
    }

    public Double getLalitude() {
        return lalitude;
    }

    public void setLalitude(Double lalitude) {
        this.lalitude = lalitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}

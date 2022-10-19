package ru.bigint.parser.metro.model;

public class MetroStation {
    private String name;
    private Integer incomingPassengers;
    private Integer outgoingPassengers;
    private String latitude;
    private String longitude;

    public MetroStation() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIncomingPassengers() {
        return incomingPassengers;
    }

    public void setIncomingPassengers(Integer incomingPassengers) {
        this.incomingPassengers = incomingPassengers;
    }

    public Integer getOutgoingPassengers() {
        return outgoingPassengers;
    }

    public void setOutgoingPassengers(Integer outgoingPassengers) {
        this.outgoingPassengers = outgoingPassengers;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
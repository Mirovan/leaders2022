package ru.bigint.parser.houses.model;

public class House {
    private String address;
    private String clearedAddress;
    private String square;
    private String latitude;
    private String longitude;

    public House() {
    }

    public House(String address, String square) {
        this.address = address;
        this.square = square;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSquare() {
        return square;
    }

    public void setSquare(String square) {
        this.square = square;
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

    public String getClearedAddress() {
        return clearedAddress;
    }

    public void setClearedAddress(String clearedAddress) {
        this.clearedAddress = clearedAddress;
    }
}
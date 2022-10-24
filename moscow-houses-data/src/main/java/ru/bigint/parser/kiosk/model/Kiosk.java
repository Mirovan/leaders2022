package ru.bigint.parser.kiosk.model;

public class Kiosk {
    private String name;
    private String admArea;
    private String district;
    private String address;
    private String nameOfBusinessEntity;
    private String latitude;
    private String longitude;

    public Kiosk() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdmArea() {
        return admArea;
    }

    public void setAdmArea(String admArea) {
        this.admArea = admArea;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNameOfBusinessEntity() {
        return nameOfBusinessEntity;
    }

    public void setNameOfBusinessEntity(String nameOfBusinessEntity) {
        this.nameOfBusinessEntity = nameOfBusinessEntity;
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
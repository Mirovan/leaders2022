package ru.bigint.parser.library.model;

public class Library {
    private String name;
    private String address;
    private String admArea;
    private String district;
    private String phone;
    private Integer numOfVisitors;
    private String latitude;
    private String longitude;

    public Library() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public String getAdmArea() {
        return admArea;
    }

    public void setAdmArea(String admArea) {
        this.admArea = admArea;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getNumOfVisitors() {
        return numOfVisitors;
    }

    public void setNumOfVisitors(Integer numOfVisitors) {
        this.numOfVisitors = numOfVisitors;
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
package ru.bigint.parser.malls.model;

public class Mall {
    private String name;
    private String about;
    private String address;
    private String clearedAddress;
    private String phone;
    private String latitude;
    private String longitude;

    public Mall() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClearedAddress() {
        return clearedAddress;
    }

    public void setClearedAddress(String clearedAddress) {
        this.clearedAddress = clearedAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
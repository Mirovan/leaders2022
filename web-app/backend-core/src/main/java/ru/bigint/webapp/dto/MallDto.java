package ru.bigint.webapp.dto;

public interface MallDto {
    Integer getId();
    String getName();
    String getAddress();
    String getAbout();
    String getPhone();
    Double getLatitude();
    Double getLongitude();
    Double getDist();
}
//public class MallDto {
//    private Integer id;
//
//    private String name;
//
//    private String address;
//
//    private String about;
//
//    private String phone;
//
//    private Double latitude;
//
//    private Double longitude;
//
//    private Double dist;
//
//    public MallDto() {
//    }
//
//    public MallDto(Integer id, String name, String address, String about, String phone, Double latitude, Double longitude, Double dist) {
//        this.id = id;
//        this.name = name;
//        this.address = address;
//        this.about = about;
//        this.phone = phone;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.dist = dist;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getAbout() {
//        return about;
//    }
//
//    public void setAbout(String about) {
//        this.about = about;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public Double getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(Double latitude) {
//        this.latitude = latitude;
//    }
//
//    public Double getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(Double longitude) {
//        this.longitude = longitude;
//    }
//
//    public Double getDist() {
//        return dist;
//    }
//
//    public void setDist(Double dist) {
//        this.dist = dist;
//    }
//}
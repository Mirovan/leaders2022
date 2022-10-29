package ru.bigint.webapp.dto;

public class AnalyticsDto {
    private String key;
    private Integer value;
    private String color;

    public AnalyticsDto() {
    }

    public AnalyticsDto(String key, Integer value, String color) {
        this.key = key;
        this.value = value;
        this.color = color;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

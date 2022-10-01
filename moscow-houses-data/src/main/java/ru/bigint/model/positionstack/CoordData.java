package ru.bigint.model.positionstack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoordData {
    private List<CoordItem> data;

    public List<CoordItem> getData() {
        return data;
    }

    public void setData(List<CoordItem> data) {
        this.data = data;
    }
}

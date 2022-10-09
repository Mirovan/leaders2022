package ru.bigint.webapp.dto;

public class Postamat {
    //Адрес
    private String address;
    //Тип места размещения
    private String placeName;
    //Число использований в месяц
    private int useMonth;

    public Postamat() {
    }

    public Postamat(String address, String placeName, int useMonth) {
        this.address = address;
        this.placeName = placeName;
        this.useMonth = useMonth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUseMonth() {
        return useMonth;
    }

    public void setUseMonth(int useMonth) {
        this.useMonth = useMonth;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
}

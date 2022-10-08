package ru.bigint.houses.model;

public class HouseHeat {
    private House house;
    private int belongPoints;

    public HouseHeat() {
    }

    public HouseHeat(House house, int belongPoints) {
        this.house = house;
        this.belongPoints = belongPoints;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public int getBelongPoints() {
        return belongPoints;
    }

    public void setBelongPoints(int belongPoints) {
        this.belongPoints = belongPoints;
    }
}
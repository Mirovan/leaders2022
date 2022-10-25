package ru.bigint.webapp.dto;

/**
 * Результат расчета - где ставить постаматы.
 * */
public class CalcPointDto {
    //Координата точки
    private Coord point;
    //весовой коэфициент = результат - чем выше, тем точка для установки приоритетнее
    private int weight;

    public CalcPointDto() {
    }

    public Coord getPoint() {
        return point;
    }

    public void setPoint(Coord point) {
        this.point = point;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

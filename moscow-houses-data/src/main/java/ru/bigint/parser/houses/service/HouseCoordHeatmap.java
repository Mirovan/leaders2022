package ru.bigint.parser.houses.service;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.util.GeometricShapeFactory;
import ru.bigint.parser.houses.model.House;
import ru.bigint.parser.houses.model.HouseHeat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class HouseCoordHeatmap {
    private static int fromIndex = 1;
    private static int toIndex = 1000;

    private static double radius = 0.005d;

    /**
     * 1) например взять некий радиус в 1 километр
     * 2) для каждой точки на карте найти все соседние точки в пределах радиуса
     * 3) посчитать для этих сумму
     * 4) отсортировать дома по убыванию по этому значению
     */
    public static List<HouseHeat> getHeatmap() {
        List<String> houses = null;
        try {
            houses = Files.readAllLines(Paths.get("C:/JavaProject/domdataparser/data/coords.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<HouseHeat> res = new ArrayList<>();
        int i = 0;
        for (var item : houses) {
            i++;
//            if (fromIndex <= i && i <= toIndex) {
            int belongPoints = 0;

            House house = getHouse(item);

            if (!house.getLatitude().equals("null") && !house.getLongitude().equals("null")) {
                GeometryFactory gf = new GeometryFactory();
                GeometricShapeFactory shape = new GeometricShapeFactory(gf);
                shape.setCentre(new Coordinate(Double.parseDouble(house.getLatitude()), Double.parseDouble(house.getLongitude())));
                shape.setSize(2 * radius);
                Polygon circle = shape.createCircle();

                for (var innerItem : houses) {
                    House checkHouse = getHouse(innerItem);
                    if (!checkHouse.getLatitude().equals("null") && !checkHouse.getLongitude().equals("null")) {
//                            System.out.println(belongPoints + ";  " + checkHouse.getLatitude() + "; " + checkHouse.getLongitude());
                        Point p = gf.createPoint(
                                new Coordinate(Double.parseDouble(checkHouse.getLatitude()), Double.parseDouble(checkHouse.getLongitude()))
                        );
                        if (circle.contains(p)) {
                            belongPoints++;
                        }
                    }
                }

                res.add(new HouseHeat(house, belongPoints));
//                }
            }
        }

        HouseHeat[] resArr = res.toArray(new HouseHeat[0]);
        Arrays.sort(resArr, Comparator.comparing(HouseHeat::getBelongPoints));
        res = new ArrayList<>(Arrays.asList(resArr));
        return res;
    }

    private static House getHouse(String item) {
        String[] arr = item.split(";");
        String address = arr[0].trim();
        String clearedAddress = arr[1].trim();
        String square = arr[2].trim();
        String latitude = arr[3].trim();
        String longitude = arr[4].trim();

        House house = new House();
        house.setAddress(address);
        house.setClearedAddress(clearedAddress);
        house.setSquare(square);
        house.setLongitude(longitude);
        house.setLatitude(latitude);
        return house;
    }

}

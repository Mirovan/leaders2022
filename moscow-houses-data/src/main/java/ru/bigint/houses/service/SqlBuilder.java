package ru.bigint.houses.service;

import ru.bigint.houses.model.House;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SqlBuilder {

    public static List<House> create() {
        List<String> houses = null;
        try {
            houses = Files.readAllLines(Paths.get("C:/JavaProject/domdataparser/data/coords.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<House> res = new ArrayList<>();
        for (var item : houses) {
            res.add(getHouse(item));
        }

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
        house.setSquare(square.replace(" ", ""));
        house.setLongitude(longitude);
        house.setLatitude(latitude);
        return house;
    }

}

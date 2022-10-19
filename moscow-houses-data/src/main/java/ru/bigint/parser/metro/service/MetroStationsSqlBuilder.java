package ru.bigint.parser.metro.service;

import ru.bigint.parser.metro.model.MetroStation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MetroStationsSqlBuilder {

    public static List<MetroStation> create() {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/data/metro-data/coords.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<MetroStation> res = new ArrayList<>();
        for (var item : lines) {
            res.add(getMetroStation(item));
        }

        return res;
    }

    private static MetroStation getMetroStation(String item) {
        String[] arr = item.split(";");
        MetroStation metroStation = new MetroStation();
        metroStation.setName(arr[0].trim());
        metroStation.setIncomingPassengers(Integer.parseInt(arr[1].trim()));
        metroStation.setOutgoingPassengers(Integer.parseInt(arr[2].trim()));
        metroStation.setLatitude(arr[3].trim());
        metroStation.setLongitude(arr[4].trim());

        return metroStation;
    }

}

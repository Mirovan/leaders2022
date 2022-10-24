package ru.bigint.parser.kiosk.service;

import ru.bigint.parser.kiosk.model.Kiosk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class KioskSqlBuilder {

    public static List<Kiosk> create() {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/data/kiosk-data/kiosk.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Kiosk> res = new ArrayList<>();
        for (var item : lines) {
            res.add(getSupermarket(item));
        }

        return res;
    }

    private static Kiosk getSupermarket(String item) {
        String[] arr = item.split(";");
        Kiosk object = new Kiosk();
        object.setName(arr[0].trim());
        object.setAddress(arr[1].trim());
        object.setAdmArea(arr[2].trim());
        object.setDistrict(arr[3].trim());
        object.setNameOfBusinessEntity(arr[4].trim());
        object.setLatitude(arr[5].trim());
        object.setLongitude(arr[6].trim());

        return object;
    }

}

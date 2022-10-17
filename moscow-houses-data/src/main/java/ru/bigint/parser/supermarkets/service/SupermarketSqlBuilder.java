package ru.bigint.parser.supermarkets.service;

import ru.bigint.parser.supermarkets.model.Supermarket;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SupermarketSqlBuilder {

    public static List<Supermarket> create() {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/data/supermarket-data/supermarkets.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Supermarket> res = new ArrayList<>();
        for (var item : lines) {
            res.add(getSupermarket(item));
        }

        return res;
    }

    private static Supermarket getSupermarket(String item) {
        String[] arr = item.split(";");
        Supermarket mall = new Supermarket();
        mall.setName(arr[0].trim());
        mall.setAddress(arr[1].trim());
        mall.setAbout(arr[2].trim());
        mall.setPhone(arr[3].trim());
        mall.setLatitude(arr[4].trim());
        mall.setLongitude(arr[5].trim());

        return mall;
    }

}

package ru.bigint.malls.service;

import ru.bigint.houses.model.House;
import ru.bigint.malls.model.Mall;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MallSqlBuilder {

    public static List<Mall> create() {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/malls-data/coords.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Mall> res = new ArrayList<>();
        for (var item : lines) {
            res.add(getMall(item));
        }

        return res;
    }

    private static Mall getMall(String item) {
        String[] arr = item.split(";");
        Mall mall = new Mall();
        mall.setName(arr[0].trim());
        mall.setAddress(arr[1].trim());
        mall.setAbout(arr[2].replace("Рейтинг: Отзыв Рейтинг:", "").trim());
        mall.setPhone(arr[3].trim());

        return mall;
    }

}

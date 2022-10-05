package ru.bigint;

import ru.bigint.model.House;
import ru.bigint.model.HouseHeat;
import ru.bigint.util.CoordParser;
import ru.bigint.util.HouseCoordHeatmap;
import ru.bigint.util.HouseParser;
import ru.bigint.util.SqlBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
//        //Парсинг домов с сайта
//        parseHouses();
//        //Получение домов и их адресов
//        getOnlyAdress();
//        //Получение координат домов
//        parseCoord();
//        //Дома с числом домов в радиусе
//        getHeatmap();
        //сохранение скрипта SQL
        createSqlQuery();
    }

    private static void createSqlQuery() {
        List<House> houses = SqlBuilder.create();

        Path output = Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/data/dump.txt");
        try {
            int i = 0;
            for (var line : houses) {
                i++;
                Files.writeString(output, "INSERT INTO houses VALUES("
                        + i + ", "
                        + "'" + line.getAddress() + "', "
                        + line.getSquare() + ", "
                        + line.getLatitude() + ", "
                        + line.getLongitude() + ");"
                        + "\n", StandardOpenOption.APPEND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getHeatmap() {
        List<HouseHeat> houses = HouseCoordHeatmap.getHeatmap();

        Path output = Paths.get("C:/JavaProject/domdataparser/data/heat.txt");
        try {
            for (var line : houses) {
                Files.writeString(output, line.getHouse().getAddress() + "; "
                        + line.getHouse().getClearedAddress() + "; "
                        + line.getHouse().getSquare() + "; "
                        + line.getHouse().getLatitude() + "; "
                        + line.getHouse().getLongitude() + "; "
                        + line.getBelongPoints() + "; "
                        + "\n", StandardOpenOption.APPEND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseCoord() {
        List<House> houses = CoordParser.parseCoords();

        Path output = Paths.get("C:/JavaProject/domdataparser/data/coords.txt");
        try {
            for (var line : houses) {
                Files.writeString(output, line.getAddress() + "; "
                        + line.getClearedAddress() + "; "
                        + line.getSquare() + "; "
                        + line.getLatitude() + "; "
                        + line.getLongitude() + "; "
                        + "\n", StandardOpenOption.APPEND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseHouses() {
        List<House> houses = HouseParser.parseHouses();
        System.out.println(houses.size());

        Path output = Paths.get("C:/JavaProject/domdataparser/data/houses.txt");
        try {
            for (var line : houses) {
                Files.writeString(output, line.getAddress() + "; " + line.getSquare() + "\n", StandardOpenOption.APPEND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getOnlyAdress() throws IOException {
        List<String> list = null;
        try {
            list = Files.readAllLines(Paths.get("C:/JavaProject/domdataparser/data/houses.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path output = Paths.get("C:/JavaProject/domdataparser/data/houses_array.txt");

        for (var line : list) {
            String address = line.substring(0, line.indexOf(";"));
            Files.writeString(output, address + "\n", StandardOpenOption.APPEND);
        }
    }
}

package ru.bigint.parser.supermarkets;

import ru.bigint.parser.supermarkets.model.Supermarket;
import ru.bigint.parser.supermarkets.service.SupermarketParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class MainSupermarkets {
    public static void main(String[] args) throws IOException {
//        //Парсинг ТЦ с сайта
        parseSupermarkets();
//        //Получение координат
//        parseCoord();
        //сохранение скрипта SQL
//        createSqlQuery();
    }

    private static void parseSupermarkets() {
        List<Supermarket> supermarkets = SupermarketParser.parseSupermarkets();

        Path output = Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/data/supermarket-data/supermarkets.txt");
        try {
            for (var line : supermarkets) {
                Files.writeString(output,
                        line.getName() + "; "
                                + line.getAddress() + "; "
                                + line.getAbout() + "; "
                                + line.getPhone() + "; "
                                + line.getLatitude() + "; "
                                + line.getLongitude() + "\n",
                        StandardOpenOption.APPEND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private static void parseCoord() {
//        List<Supermarket> houses = MallCoordParser.parseCoords();
//
//        Path output = Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/malls-data/coords.txt");
//        try {
//            for (var line : houses) {
//                Files.writeString(output,
//                        line.getName() + "; "
//                        + line.getAddress() + "; "
//                        + line.getClearedAddress() + "; "
//                        + line.getAbout() + "; "
//                        + line.getPhone() + "; "
//                        + line.getLatitude() + "; "
//                        + line.getLongitude() + "; "
//                        + "\n", StandardOpenOption.APPEND);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void createSqlQuery() {
//        List<Supermarket> houses = SupermarketSqlBuilder.create();
//
//        Path output = Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/malls-data/dump.txt");
//        try {
//            int i = 0;
//            for (var line : houses) {
//                i++;
//                Files.writeString(output, "INSERT INTO malls VALUES("
//                        + i + ", "
//                        + "'" + line.getName() + "', "
//                        + "'" + line.getAddress() + "', "
//                        + "'" + line.getAbout() + "', "
//                        + "'" + line.getPhone() + "', "
//                        + line.getLatitude() + ", "
//                        + line.getLongitude() + ");"
//                        + "\n", StandardOpenOption.APPEND);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}

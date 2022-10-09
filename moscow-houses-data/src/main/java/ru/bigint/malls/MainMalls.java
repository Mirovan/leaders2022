package ru.bigint.malls;

import ru.bigint.malls.model.Mall;
import ru.bigint.malls.service.MallCoordParser;
import ru.bigint.malls.service.MallParser;
import ru.bigint.malls.service.MallSqlBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class MainMalls {
    public static void main(String[] args) throws IOException {
//        //Парсинг ТЦ с сайта
//        parseMalls();
//        //Получение координат
//        parseCoord();
        //сохранение скрипта SQL
        createSqlQuery();
    }

    private static void parseMalls() {
        List<Mall> malls = MallParser.parseMalls();

        Path output = Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/malls-data/malls.txt");
        try {
            for (var line : malls) {
                Files.writeString(output,
                        line.getName() + "; "
                                + line.getAddress() + "; "
                                + line.getAbout() + "; "
                                + line.getPhone() + "\n",
                        StandardOpenOption.APPEND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseCoord() {
        List<Mall> houses = MallCoordParser.parseCoords();

        Path output = Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/malls-data/coords.txt");
        try {
            for (var line : houses) {
                Files.writeString(output,
                        line.getName() + "; "
                        + line.getAddress() + "; "
                        + line.getClearedAddress() + "; "
                        + line.getAbout() + "; "
                        + line.getPhone() + "; "
                        + line.getLatitude() + "; "
                        + line.getLongitude() + "; "
                        + "\n", StandardOpenOption.APPEND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createSqlQuery() {
        List<Mall> houses = MallSqlBuilder.create();

        Path output = Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/malls-data/dump.txt");
        try {
            int i = 0;
            for (var line : houses) {
                i++;
                Files.writeString(output, "INSERT INTO malls VALUES("
                        + i + ", "
                        + "'" + line.getName() + "', "
                        + "'" + line.getAddress() + "', "
                        + "'" + line.getAbout() + "', "
                        + "'" + line.getPhone() + "', "
                        + line.getLatitude() + ", "
                        + line.getLongitude() + ");"
                        + "\n", StandardOpenOption.APPEND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package ru.bigint.parser.metro;

import ru.bigint.parser.metro.model.MetroStation;
import ru.bigint.parser.metro.service.MetroStationCoordParser;
import ru.bigint.parser.metro.service.MetroStationParser;
import ru.bigint.parser.metro.service.MetroStationsSqlBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class MainMetroStations {
    public static void main(String[] args) throws IOException {
//        //Парсинг ТЦ с сайта
//        parseMetroStations();
        //Получение координат
//        parseCoord();
        //сохранение скрипта SQL
        createSqlQuery();
    }

    private static void parseMetroStations() {
        List<MetroStation> list = MetroStationParser.parseMetroStations();

        Path output = Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/data/metro-data/metrostation.txt");
        try {
            for (var line : list) {
                Files.writeString(output,
                        line.getName() + "; "
                                + line.getIncomingPassengers() + "; "
                                + line.getOutgoingPassengers() + "\n",
                        StandardOpenOption.APPEND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseCoord() {
        List<MetroStation> list = MetroStationCoordParser.parseCoords();

        Path output = Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/data/metro-data/coords.txt");
        try {
            for (var line : list) {
                Files.writeString(output,
                        line.getName() + "; "
                                + line.getIncomingPassengers() + "; "
                                + line.getOutgoingPassengers() + "; "
                                + line.getLatitude() + "; "
                                + line.getLongitude() + "; "
                                + "\n", StandardOpenOption.APPEND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createSqlQuery() {
        List<MetroStation> list = MetroStationsSqlBuilder.create();

        Path output = Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/data/metro-data/dump.txt");
        try {
            int i = 0;
            for (var line : list) {
                i++;
                Files.writeString(output, "INSERT INTO metro VALUES("
                        + i + ", "
                        + "'" + escapeSingleQuote(line.getName()) + "', "
                        + line.getIncomingPassengers() + ", "
                        + line.getOutgoingPassengers() + ", "
                        + line.getLatitude() + ", "
                        + line.getLongitude() + ");"
                        + "\n", StandardOpenOption.APPEND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String escapeSingleQuote(String str) {
        return str.replace("'", "''");
    }
}

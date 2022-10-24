package ru.bigint.parser.kiosk;

import ru.bigint.parser.kiosk.model.Kiosk;
import ru.bigint.parser.kiosk.service.KioskParser;
import ru.bigint.parser.kiosk.service.KioskSqlBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class MainKiosk {
    public static void main(String[] args) throws IOException {
//        //Парсинг Киосков с сайта
//        parseKiosk();
        //сохранение скрипта SQL
        createSqlQuery();
    }

    private static void parseKiosk() {
        List<Kiosk> kiosks = KioskParser.parseKiosk();

        Path output = Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/data/kiosk-data/kiosk.txt");
        try {
            for (var line : kiosks) {
                Files.writeString(output,
                        line.getName() + "; "
                                + line.getAddress() + "; "
                                + line.getAdmArea() + "; "
                                + line.getDistrict() + "; "
                                + line.getNameOfBusinessEntity() + "; "
                                + line.getLatitude() + "; "
                                + line.getLongitude() + "\n",
                        StandardOpenOption.APPEND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createSqlQuery() {
        List<Kiosk> kiosks = KioskSqlBuilder.create();

        Path output = Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/data/kiosk-data/dump.txt");
        try {
            int i = 0;
            for (var line : kiosks) {
                i++;
                Files.writeString(output, "INSERT INTO kiosk VALUES("
                        + i + ", "
                        + "'" + escapeSingleQuote(line.getName()) + "', "
                        + "'" + escapeSingleQuote(line.getAdmArea()) + "', "
                        + "'" + escapeSingleQuote(line.getDistrict()) + "', "
                        + "'" + escapeSingleQuote(line.getAddress()) + "', "
                        + "'" + escapeSingleQuote(line.getNameOfBusinessEntity()) + "', "
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

package ru.bigint.parser.supermarkets;

import ru.bigint.parser.supermarkets.model.Supermarket;
import ru.bigint.parser.supermarkets.service.SupermarketParser;
import ru.bigint.parser.supermarkets.service.SupermarketSqlBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class MainSupermarkets {
    public static void main(String[] args) throws IOException {
//        //Парсинг ТЦ с сайта
//        parseSupermarkets();
        //сохранение скрипта SQL
        createSqlQuery();
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

    private static void createSqlQuery() {
        List<Supermarket> supermarkets = SupermarketSqlBuilder.create();

        Path output = Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/data/supermarket-data/dump.txt");
        try {
            int i = 0;
            for (var line : supermarkets) {
                i++;
                Files.writeString(output, "INSERT INTO supermarkets VALUES("
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

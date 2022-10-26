package ru.bigint.parser.club;

import ru.bigint.parser.club.model.Club;
import ru.bigint.parser.club.service.ClubSqlBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class MainClub {
    public static void main(String[] args) throws IOException {
        //сохранение скрипта SQL
        createSqlQuery();
    }

    private static void createSqlQuery() {
        List<Club> list = ClubSqlBuilder.create();

        Path output = Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/data/club-data/dump.txt");
        try {
            int i = 0;
            for (var line : list) {
                i++;
                Files.writeString(output, "INSERT INTO club VALUES("
                        + i + ", "
                        + "'" + escapeSingleQuote(line.getName()) + "', "
                        + "'" + line.getAddress() + "', "
                        + "'" + line.getDistrict() + "', "
                        + "'" + line.getAdmArea() + "', "
                        + "'" + line.getPhone() + "', "
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

package ru.bigint.parser.mfc;

import ru.bigint.parser.mfc.model.Mfc;
import ru.bigint.parser.mfc.service.MfcSqlBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class MainMfc {
    public static void main(String[] args) throws IOException {
        //сохранение скрипта SQL
        createSqlQuery();
    }

    private static void createSqlQuery() {
        List<Mfc> list = MfcSqlBuilder.create();

        Path output = Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/data/mfc-data/dump.txt");
        try {
            int i = 0;
            for (var line : list) {
                i++;
                Files.writeString(output, "INSERT INTO mfc VALUES("
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

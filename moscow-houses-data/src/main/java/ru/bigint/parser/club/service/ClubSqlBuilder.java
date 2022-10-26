package ru.bigint.parser.club.service;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.bigint.parser.club.model.Club;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClubSqlBuilder {

    public static List<Club> create() {
        List<Club> res = new ArrayList<>();

        String data = null;
        try {
            data = new String(Files.readAllBytes(Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/data/club-data/data.json")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray arrData = new JSONArray(data);

        for (int i = 0; i < arrData.length(); i++) {
            JSONObject item = (JSONObject) arrData.get(i);

            int addressSize = item.getJSONArray("ObjectAddress").length();

            for (int j = 0; j < addressSize; j++) {
                Club club = new Club();

                club.setName(item.getString("CommonName"));

                JSONArray objectAddressArr = item.getJSONArray("ObjectAddress");
                club.setDistrict(((JSONObject) objectAddressArr.get(j)).getString("District"));
                club.setAdmArea(((JSONObject) objectAddressArr.get(j)).getString("AdmArea"));
                club.setAddress(
                        ((JSONObject) objectAddressArr.get(j)).getString("Address")
                                .replace("Российская Федерация, город Москва,", "")
                                .replace("город Москва,", "")
                                .trim()
                );

                JSONArray phoneArr = item.getJSONArray("PublicPhone");
                String phones = "";
                for (int k = 0; k < phoneArr.length(); k++) {
                    String phone = ((JSONObject) phoneArr.get(k)).getString("PublicPhone");
                    if (!Objects.isNull(phone)) phones += phone + ", ";
                }
                club.setPhone(phones.trim());

                JSONArray coord = (JSONArray) ((JSONObject) item.get("geoData")).getJSONArray("coordinates").get(j);
                if (coord != null && coord.length() >= 2) {
                    club.setLatitude(coord.get(1).toString());
                    club.setLongitude(coord.get(0).toString());
                }

                res.add(club);
            }
        }

        return res;
    }

}

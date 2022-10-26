package ru.bigint.parser.library.service;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.bigint.parser.library.model.Library;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LubrarySqlBuilder {

    public static List<Library> create() {
        List<Library> res = new ArrayList<>();

        String data = null;
        try {
            data = new String(Files.readAllBytes(Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/data/library-data/data.json")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray arrData = new JSONArray(data);

        for (int i = 0; i < arrData.length(); i++) {
            JSONObject item = (JSONObject) arrData.get(i);

            int addressSize = item.getJSONArray("ObjectAddress").length();

            for (int j = 0; j < addressSize; j++) {
                Library library = new Library();

                library.setName(item.getString("CommonName"));

                JSONArray objectAddressArr = item.getJSONArray("ObjectAddress");
                library.setDistrict(((JSONObject) objectAddressArr.get(j)).getString("District"));
                library.setAdmArea(((JSONObject) objectAddressArr.get(j)).getString("AdmArea"));
                library.setAddress(
                        ((JSONObject) objectAddressArr.get(j)).getString("Address")
                                .replace("Российская Федерация, город Москва,", "")
                                .replace("город Москва,", "")
                                .trim()
                );

                JSONArray phoneArr = item.getJSONArray("PublicPhone");
                String phones = "";
                for (int k = 0; k < phoneArr.length(); k++) {
                    String phone = ((JSONObject) phoneArr.get(k)).getString("PublicPhone");
                    if (!Objects.isNull(phone)) phones += phone + " ";
                }
                library.setPhone(phones.trim());

                JSONArray coord = (JSONArray) ((JSONObject) item.get("geoData")).getJSONArray("coordinates").get(j);
                if (coord != null && coord.length() >= 2) {
                    library.setLatitude(coord.get(1).toString());
                    library.setLongitude(coord.get(0).toString());
                }

                if (item.has("NumOfVisitors")) {
                    library.setNumOfVisitors(item.getInt("NumOfVisitors"));
                } else {
                    library.setNumOfVisitors(0);

                }

                res.add(library);
            }
        }

        return res;
    }

}

package ru.bigint.parser.mfc.service;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.bigint.parser.mfc.model.Mfc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MfcSqlBuilder {

    public static List<Mfc> create() {
        List<Mfc> res = new ArrayList<>();

        String data = null;
        try {
            data = new String(Files.readAllBytes(Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/data/mfc-data/data.json")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray arrData = new JSONArray(data);

        for (int i = 0; i < arrData.length(); i++) {
            Mfc mfc = new Mfc();
            JSONObject item = (JSONObject) arrData.get(i);
            mfc.setName(item.getString("CommonName"));
            mfc.setDistrict(item.getString("District"));
            mfc.setAdmArea(item.getString("AdmArea"));
            mfc.setAddress(
                    item.getString("Address")
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
            mfc.setPhone(phones.trim());

            JSONArray coord = ((JSONObject) item.get("geoData")).getJSONArray("coordinates");
            if (coord != null && coord.length() >= 2) {
                mfc.setLatitude(coord.get(1).toString());
                mfc.setLongitude(coord.get(0).toString());
            }

            res.add(mfc);
        }

        return res;
    }

}

package ru.bigint.parser.kiosk.service;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.bigint.parser.houses.service.ParserHttpClient;
import ru.bigint.parser.kiosk.model.Kiosk;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class KioskParser {
    private static String host =
            "https://data.mos.ru/api/rows/getresultwithcount";
    //https://data.mos.ru/api/rows/getresultwithcount?datasetId=2781&search=&sortField=Number&sortOrder=ASC&versionNumber=2&releaseNumber=457&pageNumber=1
    private static int pageNumber = 1;
    private static int maxPageNumber = 182;

    public static List<Kiosk> parseKiosk() {

        List<Kiosk> res = new ArrayList<>();

        for (int i = pageNumber; i <= maxPageNumber; i++) {
            System.out.println(i);

            Map<String, String> params = new LinkedHashMap<>();
            params.put("datasetId", "2781");
            params.put("search", "");
            params.put("sortField", "Number");
            params.put("sortOrder", "ASC");
            params.put("versionNumber", "2");
            params.put("releaseNumber", "457");
            params.put("pageNumber", String.valueOf(i));

            String mainData = ParserHttpClient.sendGet(host, params);

            JSONObject jsonData = new JSONObject(mainData);
            JSONArray arr = jsonData.getJSONArray("Result");
            for (int j = 0; j < arr.length(); j++) {
                Kiosk kiosk = new Kiosk();
                JSONObject cells = (JSONObject) ((JSONObject) arr.get(j)).get("Cells");
                kiosk.setName(cells.getString("Name"));
                kiosk.setAdmArea(cells.getString("AdmArea"));

                String address = cells.getString("Address")
                        .replace("Российская Федерация, город Москва,", "")
                        .replace("город Москва,", "")
                        .trim();
                kiosk.setAddress(address);
                kiosk.setNameOfBusinessEntity(cells.getString("NameOfBusinessEntity"));
                kiosk.setDistrict(cells.getString("District"));

                JSONArray coord = ((JSONObject) cells.get("geoData")).getJSONArray("coordinates");
                if (coord != null && coord.length() >= 2) {
                    kiosk.setLatitude(coord.get(1).toString());
                    kiosk.setLongitude(coord.get(0).toString());
                }

                res.add(kiosk);
            }
        }

        return res;
    }

}

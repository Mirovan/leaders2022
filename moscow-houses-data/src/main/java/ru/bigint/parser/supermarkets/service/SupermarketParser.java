package ru.bigint.parser.supermarkets.service;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.bigint.parser.houses.service.ParserHttpClient;
import ru.bigint.parser.supermarkets.model.Supermarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SupermarketParser {
    private static String host =
            "https://data.mos.ru/api/rows/getresultwithcount";
    //https://data.mos.ru/api/rows/getresultwithcount?datasetId=3304&search=&sortField=Number&sortOrder=ASC&versionNumber=1&releaseNumber=159&pageNumber=5
    private static int pageNumber = 2001;
    private static int maxPageNumber = 6522;
//    private static int maxPageNumber = 6522;

    public static List<Supermarket> parseSupermarkets() {

        List<Supermarket> res = new ArrayList<>();

        for (int i = pageNumber; i <= maxPageNumber; i++) {
            System.out.println(i);

            Map<String, String> params = new LinkedHashMap<>();
            params.put("datasetId", "3304");
            params.put("search", "");
            params.put("sortField", "Number");
            params.put("sortOrder", "ASC");
            params.put("versionNumber", "1");
            params.put("releaseNumber", "159");
            params.put("pageNumber", String.valueOf(i));

            String mainData = ParserHttpClient.sendGet(host, params);

            JSONObject jsonData = new JSONObject(mainData);
            JSONArray arr = jsonData.getJSONArray("Result");
            for (int j = 0; j < arr.length(); j++) {
                Supermarket supermarket = new Supermarket();
                JSONObject cells = (JSONObject) ((JSONObject) arr.get(j)).get("Cells");
                supermarket.setName(cells.getString("Name"));
                supermarket.setAbout(cells.getString("TypeService"));
                supermarket.setAddress(cells.getString("Address").replace("город Москва, ", ""));

                JSONArray phoneArr = cells.getJSONArray("PublicPhone");
                String phones = "";
                for (int k = 0; k < phoneArr.length(); k++) {
                    String phone = ((JSONObject) phoneArr.get(k)).getString("PublicPhone");
                    if (!Objects.isNull(phone)) phones += phone + " ";
                }
                supermarket.setPhone(phones.trim());
                JSONArray coord = ((JSONObject) cells.get("geoData")).getJSONArray("coordinates");
                if (coord != null && coord.length() >= 2) {
                    supermarket.setLatitude(coord.get(1).toString());
                    supermarket.setLongitude(coord.get(0).toString());
                }

                res.add(supermarket);
            }
        }

        return res;
    }

}

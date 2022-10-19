package ru.bigint.parser.metro.service;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.bigint.parser.houses.service.ParserHttpClient;
import ru.bigint.parser.metro.model.MetroStation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MetroStationParser {
    private static String host =
            "https://data.mos.ru/api/rows/getresultwithcount";
    //https://data.mos.ru/api/rows/getresultwithcount?datasetId=62743&search=&filters[0].Key=Year&filters[0].Value=2022&filters[1].Key=Quarter&filters[1].Value=II%20%D0%BA%D0%B2%D0%B0%D1%80%D1%82%D0%B0%D0%BB&sortField=Number&sortOrder=ASC&versionNumber=1&releaseNumber=13&pageNumber=1

    public static List<MetroStation> parseMetroStations() {

        List<MetroStation> res = new ArrayList<>();

        //Всего 29 страниц
        for (int i = 1; i < 29; i++) {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("datasetId", "62743");
            params.put("search", "");
            params.put("filters[0].Key", "Year");
            params.put("filters[0].Value", "2022");
            params.put("filters[1].Key", "Quarter");
            params.put("filters[1].Value", "II квартал");
            params.put("sortField", "Number");
            params.put("sortOrder", "ASC");
            params.put("versionNumber", "1");
            params.put("releaseNumber", "13");
            params.put("pageNumber", String.valueOf(i));

            String mainData = ParserHttpClient.sendGet(host, params);

            JSONObject jsonData = new JSONObject(mainData);
            JSONArray arr = jsonData.getJSONArray("Result");
            for (int j = 0; j < arr.length(); j++) {
                MetroStation metroStation = new MetroStation();
                JSONObject cells = (JSONObject) ((JSONObject) arr.get(j)).get("Cells");
                metroStation.setName(cells.getString("NameOfStation"));
                metroStation.setIncomingPassengers(cells.getInt("IncomingPassengers"));
                metroStation.setOutgoingPassengers(cells.getInt("OutgoingPassengers"));

                res.add(metroStation);
            }
        }

        return res;
    }

}

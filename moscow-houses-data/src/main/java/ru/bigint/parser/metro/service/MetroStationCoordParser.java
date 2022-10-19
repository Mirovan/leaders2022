package ru.bigint.parser.metro.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.bigint.core.model.positionstack.CoordData;
import ru.bigint.core.model.positionstack.CoordItem;
import ru.bigint.parser.houses.service.ParserHttpClient;
import ru.bigint.parser.metro.model.MetroStation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MetroStationCoordParser {
    private static String host = "http://api.positionstack.com/v1/forward";
    private static String accessKey = "fca2e3beebc5e2a7e9be126b89bf2b99";

    public static List<MetroStation> parseCoords() {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/data/metro-data/metrostation.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<MetroStation> res = new ArrayList<>();
        for (var line : lines) {
            String[] arr = line.split(";");
            MetroStation metroStation = new MetroStation();
            metroStation.setName(arr[0]);
            metroStation.setIncomingPassengers(Integer.parseInt(arr[1].trim()));
            metroStation.setOutgoingPassengers(Integer.parseInt(arr[2].trim()));

            Map<String, String> params = new HashMap<>();
            params.put("access_key", accessKey);
            params.put("query", "Москва станция " + metroStation.getName());

            String data = ParserHttpClient.sendGet(host, params);

            CoordData coordData = null;
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
                coordData = mapper.readValue(data, CoordData.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            if (!Objects.isNull(coordData) && !Objects.isNull(coordData.getData())) {
                List<CoordItem> coords = coordData.getData().stream()
                        .filter(item ->
                                Objects.nonNull(item)
                                        && item.getCountry().toLowerCase().contains("rus")
                                        && item.getRegion().toLowerCase().contains("mos")
                        ).toList();

                if (!coords.isEmpty()) {
                    CoordItem coordItem = coords.get(0);
                    metroStation.setLatitude(coordItem.getLatitude());
                    metroStation.setLongitude(coordItem.getLongitude());
                }
            }

            res.add(metroStation);
        }

        return res;
    }

}
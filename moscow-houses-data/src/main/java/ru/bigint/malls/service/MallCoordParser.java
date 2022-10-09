package ru.bigint.malls.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.bigint.houses.model.House;
import ru.bigint.houses.service.ParserHttpClient;
import ru.bigint.malls.model.Mall;
import ru.bigint.model.positionstack.CoordData;
import ru.bigint.model.positionstack.CoordItem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MallCoordParser {
    private static String host = "http://api.positionstack.com/v1/forward";
    private static String accessKey = "fca2e3beebc5e2a7e9be126b89bf2b99";

    public static List<Mall> parseCoords() {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("C:/JavaProject/leaders2022/moscow-houses-data/malls-data/malls.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Mall> res = new ArrayList<>();
        for (var line : lines) {
            String[] arr = line.split(";");
            Mall mall = new Mall();
            mall.setName(arr[0]);
            mall.setAddress(arr[1]);
            mall.setClearedAddress(clearAddress("Москва " + arr[1]));
            mall.setAbout(arr[2]);
            mall.setPhone(arr[3]);

            Map<String, String> params = new HashMap<>();
            params.put("access_key", accessKey);
            params.put("query", mall.getClearedAddress());

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
                                        && item.getType().toLowerCase().contains("address")
                        )
                        .collect(Collectors.toList());

                if (!coords.isEmpty()) {
                    CoordItem coordItem = coords.get(0);
                    mall.setLatitude(coordItem.getLatitude());
                    mall.setLongitude(coordItem.getLongitude());
                }
            }

            res.add(mall);
        }

        return res;
    }

    private static String clearAddress(String address) {
        address = address.replace("ул.", "");
        address = address.replace("корпус", "");

        return address;
    }

}

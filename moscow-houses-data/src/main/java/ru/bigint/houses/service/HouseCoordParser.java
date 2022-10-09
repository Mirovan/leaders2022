package ru.bigint.houses.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.bigint.houses.model.House;
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

public class HouseCoordParser {
    private static String host = "http://api.positionstack.com/v1/forward";
    private static String accessKey = "fca2e3beebc5e2a7e9be126b89bf2b99";
    private static int fromIndex = 33001;
    private static int toIndex = 38000;

    public static List<House> parseCoords() {
        List<String> addressSquareList = null;
        try {
            addressSquareList = Files.readAllLines(Paths.get("C:/JavaProject/domdataparser/data/houses.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<House> res = new ArrayList<>();
        int i = 0;
        for (var addressSquare : addressSquareList) {
            i++;
            if (fromIndex <= i && i <= toIndex) {
                String address = addressSquare.substring(0, addressSquare.indexOf(";"));
                String clearedAddress = clearAddress(address);
                String square = addressSquare.substring(addressSquare.indexOf(";") + 1);

                Map<String, String> params = new HashMap<>();
                params.put("access_key", accessKey);
                params.put("query", clearedAddress);

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

                    House house = new House(address, square);
                    house.setClearedAddress(clearedAddress);
                    if (!coords.isEmpty()) {
                        CoordItem coordItem = coords.get(0);
                        house.setLatitude(coordItem.getLatitude());
                        house.setLongitude(coordItem.getLongitude());
                    }
                    res.add(house);
                }
            }
        }

        return res;
    }

    private static String clearAddress(String address) {
        String[] arr = address.split(",");

        String city = arr[0].trim().replace("г.", "").trim();

        String street = arr[1].trim();
        Pattern p = Pattern.compile("[\u0410-\u042F]");
        Matcher m = p.matcher(street);
        if (m.find()) {
            int position = m.start();
            street = street.substring(position);
//            if (street.indexOf(" ") > 0) street = street.substring(0, street.indexOf(" "));
        }
        street = street.replace("Стар.", "");
        street = street.replace("Нов.", "");
        street = street.replace("Б.", "");
        street = street.replace("М.", "");

        String number = address.substring(address.indexOf("д. ") + 3);
        if (number.indexOf(",") > 0) number = number.substring(0, number.indexOf(","));
        number = number.replace("/", " ");

        return city + " " + street + " " + number;
    }

}

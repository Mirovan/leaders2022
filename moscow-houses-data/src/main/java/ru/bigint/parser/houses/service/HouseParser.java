package ru.bigint.parser.houses.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.bigint.parser.houses.model.House;
import ru.bigint.parser.houses.model.YearLink;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HouseParser {
    private static String host = "https://domdata.ru";
    private static String listYearsUrl = host + "/god";
    private static int fromYear = 2000;
    private static int toYear = 2022;

    private static List<YearLink> parseYears(String mainData) {
        Document doc = Jsoup.parse(mainData);
        Elements aElements = doc.select(".content ul.views-summary li a");

        List<YearLink> list = new ArrayList<>();
        for (Element elem : aElements) {
            list.add(new YearLink(elem.text(), host + "/" + elem.attr("href")));
        }

        return list;
    }

    public static List<House> parseHouses() {
        String mainData = ParserHttpClient.sendGet(listYearsUrl, new HashMap<>());
        List<YearLink> listYears = HouseParser.parseYears(mainData);

        List<House> houses = new ArrayList<>();
        for (YearLink yearLink: listYears) {
            int year = Integer.parseInt(yearLink.getText());
            if (year < fromYear || year > toYear) continue;

            String housesDataByYear = ParserHttpClient.sendGet(yearLink.getUrl(), new HashMap<>());
            Document doc = Jsoup.parse(housesDataByYear);
            Elements aElements = doc.select(".content .item-list ol li a");
            for (Element elem : aElements) {
                houses.add(parseHouse(host + "/" + elem.attr("href")));
            }
        }

        return houses;
    }

    public static House parseHouse(String href) {
        String mainData = ParserHttpClient.sendGet(href, new HashMap<>());
        Document doc = Jsoup.parse(mainData);
        Elements aElements = doc.select(".required-fields div.field-label");

        var house = new House();
        for (Element elem : aElements) {
            if (elem.text().contains("Адрес дома")) {
                house.setAddress(elem.nextElementSibling().child(0).text());
            }
            if (elem.text().contains("Общая площадь жилых помещений")) {
                house.setSquare(elem.nextElementSibling().child(0).text());
            }
        }

        return house;
    }
}

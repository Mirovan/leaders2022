package ru.bigint.malls.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.bigint.houses.model.House;
import ru.bigint.houses.model.YearLink;
import ru.bigint.houses.service.ParserHttpClient;
import ru.bigint.malls.model.Mall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MallParser {
//    private static String host = "https://msk.ros-spravka.ru/catalog/shopping_malls/";
    private static String host = "https://msk.ros-spravka.ru/catalog/shopping_malls/?PAGEN_1=2";

    public static List<Mall> parseMalls() {
        String mainData = ParserHttpClient.sendGet(host, new HashMap<>());
        Document doc = Jsoup.parse(mainData);
        Elements aElements = doc.select(".rubric_company .element");

        List<Mall> res = new ArrayList<>();

        for (Element elem: aElements) {
            Mall mall = new Mall();

            Elements nameElem = elem.select(".element-title > span");
            Elements aboutElem = elem.select("div");
            Elements addressElem = elem.select(".element > .grey_border > span");
            Elements phonesElem = elem.select(".element > .grey_border > .phones > span");

            mall.setName(nameElem.text().replace(",",""));
            mall.setAbout(aboutElem.text());
            mall.setAddress(addressElem.text());
            mall.setPhone(phonesElem.text());

            res.add(mall);
        }

        return res;
    }

}

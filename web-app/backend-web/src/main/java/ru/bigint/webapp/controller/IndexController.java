package ru.bigint.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.bigint.webapp.data.entity.House;
import ru.bigint.webapp.service.iface.house.HouseService;

import java.util.List;


@Controller
public class IndexController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final HouseService houseService;

    public IndexController(HouseService houseService) {
        this.houseService = houseService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        List<House> list = houseService.getAll();

        String kml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://earth.google.com/kml/2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n" +
                "    <Document>\n" +
                "        <name>Moscow heatmap</name>\n" +
                "        <Folder>\n" +
                "            <name>Moscow heatmap 1</name>\n" +
                "            <Placemark id=\"Москва Ленинградский 33\">\n" +
                "              <name> г. Москва, пр-кт. Ленинградский, д. 33, к. 8</name>\n" +
                "              <ExtendedData><Data name=\"area\"><value>2415.0</value></Data></ExtendedData>\n" +
                "              <Point><coordinates>37.556659,55.788784,0</coordinates></Point>\n" +
                "            </Placemark>\n" +
                "            <Placemark id=\"Москва Металлургов 48\">\n" +
                "              <name> г. Москва, ул. Металлургов, д. 48, к. 2</name>\n" +
                "              <ExtendedData><Data name=\"area\"><value>5136.7</value></Data></ExtendedData>\n" +
                "              <Point><coordinates>37.801087,55.760433,0</coordinates></Point>\n" +
                "            </Placemark>\n" +
                "        </Folder>\n" +
                "    </Document>\n" +
                "</kml>\n";

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("list", list);
        modelAndView.addObject("kml", kml);
        modelAndView.setViewName("index");
        return modelAndView;
    }

}

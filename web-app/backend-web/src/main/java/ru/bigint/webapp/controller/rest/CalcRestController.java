package ru.bigint.webapp.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bigint.webapp.dto.CalcPointDto;
import ru.bigint.webapp.dto.ColoredPolygon;
import ru.bigint.webapp.service.iface.CalcService;

import java.util.List;


@RestController
@RequestMapping(value = "/api/calc")
public class CalcRestController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final CalcService calcService;

    public CalcRestController(CalcService calcService) {
        this.calcService = calcService;
    }

    @GetMapping()
    public List<ColoredPolygon> calculate(
            @RequestParam(value = "radius") int radius,
            @RequestParam(value = "kmlId") String kmlId,
            @RequestParam(value = "considerHouses") boolean considerHouses,
            @RequestParam(value = "considerMalls") boolean considerMalls,
            @RequestParam(value = "considerSupermarkets") boolean considerSupermarkets,
            @RequestParam(value = "considerMetro") boolean considerMetro,
            @RequestParam(value = "considerWorkCenter") boolean considerWorkCenter,
            @RequestParam(value = "considerChildHouse") boolean considerChildHouse,
            @RequestParam(value = "considerParking") boolean considerParking,
            @RequestParam(value = "considerPostamat") boolean considerPostamat
            ) {
        return calcService.calculate(radius, kmlId, considerHouses, considerMalls, considerSupermarkets, considerMetro,
                considerPostamat, considerWorkCenter, considerChildHouse, considerParking);
    }

}

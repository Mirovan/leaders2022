package ru.bigint.webapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.SupermarketDto;
import ru.bigint.webapp.service.iface.SupermarketService;

import java.util.List;

@RestController
@RequestMapping(value = "/supermarkets")
public class SupermarketController {

    private final SupermarketService supermarketService;

    public SupermarketController(SupermarketService supermarketService) {
        this.supermarketService = supermarketService;
    }

    @GetMapping("/nearest")
    public List<SupermarketDto> getNearestMalls(@RequestParam(value = "latitude", required = false) Double latitude,
                                                @RequestParam(value = "longitude", required = false) Double longitude) {
        List<SupermarketDto> supermarkets = supermarketService.findNearest(new Coord(latitude, longitude), 5);
        return supermarkets;
    }

}

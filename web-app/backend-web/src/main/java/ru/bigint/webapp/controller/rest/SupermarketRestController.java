package ru.bigint.webapp.controller.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.SupermarketDto;
import ru.bigint.webapp.service.iface.SupermarketService;

import java.util.List;

@Tag(name = "supermarkets", description = "Работа с данными супермаркетов")
@RestController
@RequestMapping(value = "/api/supermarkets")
public class SupermarketRestController {

    private final SupermarketService supermarketService;

    public SupermarketRestController(SupermarketService supermarketService) {
        this.supermarketService = supermarketService;
    }

    @GetMapping("/nearest")
    public List<SupermarketDto> getNearestMalls(@RequestParam(value = "latitude", required = false) Double latitude,
                                                @RequestParam(value = "longitude", required = false) Double longitude) {
        return supermarketService.findNearest(new Coord(latitude, longitude), 3);
    }

}

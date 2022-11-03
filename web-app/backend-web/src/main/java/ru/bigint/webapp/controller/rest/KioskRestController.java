package ru.bigint.webapp.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.KioskDto;
import ru.bigint.webapp.service.iface.KioskService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/kiosk")
public class KioskRestController {

    private final KioskService kioskService;

    public KioskRestController(KioskService kioskService) {
        this.kioskService = kioskService;
    }

    @GetMapping("/nearest")
    public List<KioskDto> getNearestMalls(@RequestParam(value = "latitude", required = false) Double latitude,
                                          @RequestParam(value = "longitude", required = false) Double longitude) {
        return kioskService.findNearest(new Coord(latitude, longitude), 3);
    }

}

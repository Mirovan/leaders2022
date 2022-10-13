package ru.bigint.webapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.MallDto;
import ru.bigint.webapp.service.iface.MallService;

import java.util.List;

@RestController
@RequestMapping(value = "/malls")
public class MallController {

    private final MallService mallService;

    public MallController(MallService mallService) {
        this.mallService = mallService;
    }

    @GetMapping("/nearest")
    public List<MallDto> getNearestMalls(@RequestParam(value = "latitude", required = false) Double latitude,
                                         @RequestParam(value = "longitude", required = false) Double longitude) {
        List<MallDto> malls = mallService.findNearest(new Coord(latitude, longitude), 5);
        return malls;
    }

}

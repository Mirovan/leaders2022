package ru.bigint.webapp.controller.rest;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.MallDto;
import ru.bigint.webapp.service.iface.MallService;

import java.util.List;

@Api(tags = { "Торговые центры" })
@Tag(name = "Торговые центры", description = "Работа с данными торговых центров")
@RestController
@RequestMapping(value = "/api/malls")
public class MallRestController {

    private final MallService mallService;

    public MallRestController(MallService mallService) {
        this.mallService = mallService;
    }

    @GetMapping("/nearest")
    public List<MallDto> getNearestMalls(@RequestParam(value = "latitude", required = false) Double latitude,
                                         @RequestParam(value = "longitude", required = false) Double longitude) {
        List<MallDto> malls = mallService.findNearest(new Coord(latitude, longitude), 3);
        return malls;
    }

}

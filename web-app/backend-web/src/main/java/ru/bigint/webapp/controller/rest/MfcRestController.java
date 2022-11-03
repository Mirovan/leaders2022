package ru.bigint.webapp.controller.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.KioskDto;
import ru.bigint.webapp.dto.MfcDto;
import ru.bigint.webapp.service.iface.MfcService;

import java.util.List;

@Tag(name = "mfc", description = "Работа с данными МФЦв")
@RestController
@RequestMapping(value = "/api/mfc")
public class MfcRestController {

    private final MfcService mfcService;

    public MfcRestController(MfcService mfcService) {
        this.mfcService = mfcService;
    }

    @GetMapping("/nearest")
    public List<MfcDto> getNearestMalls(@RequestParam(value = "latitude", required = false) Double latitude,
                                        @RequestParam(value = "longitude", required = false) Double longitude) {
        return mfcService.findNearest(new Coord(latitude, longitude), 3);
    }

}

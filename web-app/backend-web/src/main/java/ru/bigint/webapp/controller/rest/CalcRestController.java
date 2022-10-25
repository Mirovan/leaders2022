package ru.bigint.webapp.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bigint.webapp.dto.CalcPointDto;
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
    public List<CalcPointDto> calculate() {
        return calcService.calculate(200, "fad2e20b-f3d1-431d-b5ba-e020986f847a");
    }

}

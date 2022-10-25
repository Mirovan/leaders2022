package ru.bigint.webapp.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bigint.webapp.service.iface.HouseService;

import java.util.List;


@RestController
@RequestMapping(value = "/api/calc")
public class CalcRestController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final HouseService houseService;

    public CalcRestController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping(value = "/")
    public List<String> calculate() {
        return null;
    }

}

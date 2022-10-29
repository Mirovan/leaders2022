package ru.bigint.webapp.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bigint.webapp.dto.AnalyticsDto;
import ru.bigint.webapp.entity.Postamat;
import ru.bigint.webapp.service.iface.AnalyticsService;
import ru.bigint.webapp.service.iface.PostamatService;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/analytics")
public class AnalyticsRestController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PostamatService postamatService;
    private final AnalyticsService analyticsService;

    public AnalyticsRestController(PostamatService postamatService, AnalyticsService analyticsService) {
        this.postamatService = postamatService;
        this.analyticsService = analyticsService;
    }

    @GetMapping("/postamat/using/all")
    public List<AnalyticsDto> getPostamatUsing() {
        return analyticsService.getUsingStat();
    }

    @GetMapping("/postamat/using")
    public Postamat getPostamatUsing(@RequestParam int id) {
        return postamatService.getById(id);
    }

}

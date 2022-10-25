package ru.bigint.webapp.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.bigint.webapp.entity.Postamat;
import ru.bigint.webapp.service.iface.PostamatService;

import java.util.List;


@RestController
@RequestMapping(value = "/api/analytics")
public class AnalyticsRestController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PostamatService postamatService;

    public AnalyticsRestController(PostamatService postamatService) {
        this.postamatService = postamatService;
    }

    @GetMapping("/postamat/using")
    public Postamat getPostamatUsing(@RequestParam int id) {
        return postamatService.getById(id);
    }

}

package ru.bigint.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.bigint.webapp.entity.House;
import ru.bigint.webapp.entity.KmlJob;
import ru.bigint.webapp.service.iface.HouseService;
import ru.bigint.webapp.service.iface.KmlJobService;

import java.util.List;


@Controller
public class IndexController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final HouseService houseService;
    private final KmlJobService kmlJobService;

    public IndexController(HouseService houseService, KmlJobService kmlJobService) {
        this.houseService = houseService;
        this.kmlJobService = kmlJobService;
    }

    @GetMapping(value = "/")
    public ModelAndView index() {
        List<House> list = houseService.getAll();
        List<KmlJob> kmlJobs = kmlJobService.listAllOrderedByDate();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("list", list);
        modelAndView.addObject("heatMaps", kmlJobs);
        modelAndView.setViewName("index");
        return modelAndView;
    }

}

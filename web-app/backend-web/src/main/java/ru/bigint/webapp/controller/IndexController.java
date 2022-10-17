package ru.bigint.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.bigint.webapp.entity.House;
import ru.bigint.webapp.service.iface.HouseService;

import java.util.List;


@Controller
public class IndexController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final HouseService houseService;

    public IndexController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping(value = "/")
    public ModelAndView index() {
        List<House> list = houseService.getAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("list", list);
        modelAndView.setViewName("index");
        return modelAndView;
    }

}

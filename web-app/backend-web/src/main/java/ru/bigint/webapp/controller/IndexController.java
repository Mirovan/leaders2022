package ru.bigint.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.bigint.webapp.data.entity.House;
import ru.bigint.webapp.service.iface.house.HouseService;

import java.util.List;


@Controller
public class IndexController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final HouseService houseService;

    public IndexController(HouseService houseService) {
        this.houseService = houseService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        List<House> list = houseService.getAll();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("list", list);
        modelAndView.setViewName("index");
        return modelAndView;
    }

}

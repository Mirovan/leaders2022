package ru.bigint.webapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.bigint.webapp.service.iface.MallService;

@RestController
@RequestMapping(value = "/generator")
public class GeneratorController {

    @GetMapping()
    public ModelAndView generatorIndex() {
        return new ModelAndView("generator/index");
    }

}

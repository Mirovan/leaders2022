package ru.bigint.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.bigint.webapp.entity.Postamat;
import ru.bigint.webapp.entity.PostamatUsing;
import ru.bigint.webapp.service.iface.PostamatService;

import java.util.List;
import java.util.Random;


@Controller
@RequestMapping(value = "/postamats")
public class PostamatController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PostamatService postamatService;

    public PostamatController(PostamatService postamatService) {
        this.postamatService = postamatService;
    }

    @GetMapping()
    public ModelAndView getAllPostamats() {
        List<Postamat> postamats = postamatService.getAll();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("postamats", postamats);
        modelAndView.setViewName("postamat/index");
        return modelAndView;
    }

}

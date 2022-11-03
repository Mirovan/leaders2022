package ru.bigint.webapp.controller.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.bigint.webapp.entity.Postamat;
import ru.bigint.webapp.service.iface.PostamatService;

import java.util.List;

@Tag(name = "postamats", description = "Сервис для работы с постаматами")
@RestController
@RequestMapping(value = "/api/postamats")
public class PostamatRestController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PostamatService postamatService;

    public PostamatRestController(PostamatService postamatService) {
        this.postamatService = postamatService;
    }

    @GetMapping("/list")
    public List<Postamat> getAllPostamatsList() {
        return postamatService.getAll();
    }

    @PostMapping("/save")
    public Postamat savePostamat(@RequestBody Postamat postamat) {
        return postamatService.add(postamat);
    }

}

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
import ru.bigint.webapp.dto.PostamatDto;
import ru.bigint.webapp.entity.Postamat;
import ru.bigint.webapp.service.iface.PostamatService;

import java.util.ArrayList;
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
    public ModelAndView getAllPostamat() {
        List<PostamatDto> postamatsStub = List.of(
                new PostamatDto("ул. Цветной бульвар, 15 , корпус 1", "Цветной универмаг", 10217),
                new PostamatDto("ул. Маршала Бирюзова, 32", "ТРЦ Пятая Авеню", 6224),
                new PostamatDto("ул. Манежная площадь, 1 , корпус 2", "Охотный ряд", 12241),
                new PostamatDto("ул. МКАД 24 км, 1", "Вегас", 861),
                new PostamatDto("ул. Верхняя Красносельская, 3а", "Тройка", 2234),
                new PostamatDto("Гипермаркет Твой дом на 24 км МКАД", "ул. 24 км МКАД, 1 , корпус 1", 4204)
        );

        //Добавляем постаматы тестовые данные - заглушки
        List<Postamat> postamats = new ArrayList<>();
        postamats.addAll(
            postamatsStub.stream()
                    .map(item -> {
                        var postamat = new Postamat();
                        postamat.setAddress(item.getAddress());
                        postamat.setPlace(item.getPlaceName());
                        postamat.setUseMonth(item.getUseMonth());
                        return postamat;
                    })
                    .toList()
        );

        //постаматы из БД
        postamats.addAll(postamatService.getAll());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("postamats", postamats);
        modelAndView.setViewName("postamat/index");
        return modelAndView;
    }

    @PostMapping("/save")
    @ResponseBody
    public Postamat savePostamat(@RequestBody Postamat postamat) {
        postamat.setUseMonth(getRandomNumber(800, 20000));
        return postamatService.add(postamat);
    }

    private int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}

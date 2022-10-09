package ru.bigint.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.bigint.webapp.dto.Postamat;

import java.util.List;


@Controller
@RequestMapping(value = "/postamat")
public class PostamatController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @GetMapping()
    public ModelAndView getAllPostamat() {
        List<Postamat> postamats = List.of(
                new Postamat("ул. Цветной бульвар, 15 , корпус 1", "Цветной универмаг", 10217),
                new Postamat("ул. Маршала Бирюзова, 32", "ТРЦ Пятая Авеню", 6224),
                new Postamat("ул. Манежная площадь, 1 , корпус 2", "Охотный ряд", 12241),
                new Postamat("ул. МКАД 24 км, 1", "Вегас", 861),
                new Postamat("ул. Верхняя Красносельская, 3а", "Тройка", 2234),
                new Postamat("Гипермаркет Твой дом на 24 км МКАД", "ул. 24 км МКАД, 1 , корпус 1", 4204)
        );
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("postamats", postamats);
        modelAndView.setViewName("postamat/index");
        return modelAndView;
    }

}

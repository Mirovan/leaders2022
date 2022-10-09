package ru.bigint.webapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bigint.webapp.entity.Mall;

import java.util.List;

@RestController
@RequestMapping(value = "/malls")
public class MallController {

    @GetMapping("/nearest")
    public List<Mall> getNearestMalls(@PathVariable(required = false) Double longitude,
                                      @PathVariable(required = false) Double latitude) {
        //ToDo: вызов сервиса с методом получения ближайших ТЦ
        List<Mall> malls = List.of(
                new Mall("Цветной универмаг", "ул. Цветной бульвар, 15 , корпус 1", "описание", "+7 (495) 737-77-73", 55.771138, 37.620745),
                new Mall("ТРЦ Пятая Авеню", "ул. Маршала Бирюзова, 32", "описание", "+7 (495) 925-27-00", 55.799589, 37.48236),
                new Mall("Тройка", "ул. Верхняя Красносельская, 3а", "описание", "+7 (999) 900-33-33", 55.785864, 37.661382),
                new Mall("Охотный ряд", "ул. Манежная площадь, 1 , корпус 2", "описание", "+7 (495) 737-84-49", 55.756205, 37.613987)
        );
        return malls;
    }

}

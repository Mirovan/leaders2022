package ru.bigint.webapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.MallDto;
import ru.bigint.webapp.service.iface.MallService;

import java.util.List;

@RestController
@RequestMapping(value = "/malls")
public class MallController {

    private final MallService mallService;

    public MallController(MallService mallService) {
        this.mallService = mallService;
    }

    @GetMapping("/nearest")
    public List<MallDto> getNearestMalls(@RequestParam(value = "latitude", required = false) Double latitude,
                                         @RequestParam(value = "longitude", required = false) Double longitude) {
        //ToDo: вызов сервиса с методом получения ближайших ТЦ

        List<MallDto> malls = mallService.findNearest(new Coord(latitude, longitude), 5);

//        List<Mall> malls = mallDtoList.stream()
//                .map(item -> new Mall(
//                        item.getId(),
//                        item.getName(),
//                        item.getAddress(),
//                        item.getAbout(),
//                        item.getPhone(),
//                        item.getLatitude(),
//                        item.getLongitude()
//                        )
//                )
//                .toList();

//        List<Mall> malls = new ArrayList<>();
//
//        malls.addAll(List.of(
//                new Mall(1, "Цветной универмаг", "ул. Цветной бульвар, 15 , корпус 1", "описание", "+7 (495) 737-77-73", 55.771138, 37.620745),
//                new Mall(2, "ТРЦ Пятая Авеню", "ул. Маршала Бирюзова, 32", "описание", "+7 (495) 925-27-00", 55.799589, 37.48236),
//                new Mall(3, "Тройка", "ул. Верхняя Красносельская, 3а", "описание", "+7 (999) 900-33-33", 55.785864, 37.661382),
//                new Mall(4, "Охотный ряд", "ул. Манежная площадь, 1 , корпус 2", "описание", "+7 (495) 737-84-49", 55.756205, 37.613987)
//        ));
        return malls;
    }

}

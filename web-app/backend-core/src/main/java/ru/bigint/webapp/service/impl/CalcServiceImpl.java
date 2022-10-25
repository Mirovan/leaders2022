package ru.bigint.webapp.service.impl;

import org.springframework.stereotype.Service;
import ru.bigint.webapp.dto.CalcPointDto;
import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.service.iface.CalcService;
import ru.bigint.webapp.service.iface.HouseService;
import ru.bigint.webapp.service.iface.KmlJobService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class CalcServiceImpl implements CalcService {

    private final KmlJobService kmlJobService;
    private final HouseService houseService;

    public CalcServiceImpl(KmlJobService kmlJobService, HouseService houseService) {
        this.kmlJobService = kmlJobService;
        this.houseService = houseService;
    }

    @Override
    public List<CalcPointDto> calculate(int radius, String kmlId) {
        List<CalcPointDto> res = new ArrayList<>();

        byte[] kml = kmlJobService.getKmlResultById(kmlId);
        String kmlStr = new String(kml, StandardCharsets.UTF_8);
        String[] arr = kmlStr.split("\n");

        //тепловая карта. Весовой коэф от 0 до 9 - в зависимости от теплоты (0.0 .. 1.0)
        for (String item: arr) {
            String[] pointData = item.split(" ");
            double w = Double.parseDouble(pointData[1]) * 10;
            double lon = Double.parseDouble(pointData[2]);
            double lat = Double.parseDouble(pointData[3]);

            CalcPointDto calcPointDto = new CalcPointDto();
            calcPointDto.setWeight((int) w);
            calcPointDto.setPoint(new Coord(lat, lon));
            res.add(calcPointDto);
        }

        res.sort(Comparator.comparingInt(CalcPointDto::getWeight).reversed());

        //ToDo: убрать дубликаты точек из списка

        return res;
    }
}

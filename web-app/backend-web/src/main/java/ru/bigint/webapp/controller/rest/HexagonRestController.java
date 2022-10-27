package ru.bigint.webapp.controller.rest;

import org.geotools.referencing.GeodeticCalculator;
import org.locationtech.jts.geom.GeometryFactory;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bigint.webapp.dto.Coord;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/hexagon")
public class HexagonRestController {

    private final int hexagonRadius = 300;

    /**
     * Тестовый метод для генерации гексагональной сетки
     */
    @GetMapping()
    public List<Double[][]> generate() throws FactoryException, TransformException {
        List<Double[][]> hexGrid = new ArrayList<>();

        Coord cityCenter = new Coord(55.751244, 37.618423);

        GeodeticCalculator calc = new GeodeticCalculator();
        double lenX = 2 * Math.sqrt(Math.pow(hexagonRadius,2) - Math.pow(hexagonRadius/2,2));

        //Генерим N*N точек с центрами гексагонов
        //Для каждой точки вычисляем координаты гексагона
        int n = 10;

        for (int i=-n; i<n; i++) {
            //Вычисление центров гексагонов по оси X
            calc.setStartingGeographicPoint(cityCenter.getLongitude(), cityCenter.getLatitude());

            calc.setDirection(90, i * lenX);
            Point2D hexagonCenter = calc.getDestinationGeographicPoint();

            Double[][] hexagon = new Double[7][2];
            for (int k = 0; k < 7; k++) {
                calc.setStartingGeographicPoint(hexagonCenter.getX(), hexagonCenter.getY());
                calc.setDirection(k * 60, hexagonRadius);
                Point2D point = calc.getDestinationGeographicPoint();

                Double[] coord = new Double[2];
                coord[0] = point.getX();
                coord[1] = point.getY();

                hexagon[k] = coord;
            }
            hexGrid.add(hexagon);
        }

        return hexGrid;
    }

}

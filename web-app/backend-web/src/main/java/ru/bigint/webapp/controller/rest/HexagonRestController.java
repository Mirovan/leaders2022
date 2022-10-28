package ru.bigint.webapp.controller.rest;

import org.geotools.referencing.GeodeticCalculator;
import org.locationtech.jts.geom.GeometryFactory;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bigint.webapp.dto.Coord;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/hexagon")
public class HexagonRestController {

//    private final int hexagonRadius = 300;

    /**
     * Тестовый метод для генерации гексагональной сетки
     */
    @GetMapping()
    public List<Double[][]> generate(@RequestParam(value = "hexagonRadius", defaultValue = "300") Integer hexagonRadius)
            throws FactoryException, TransformException {
        List<Double[][]> hexGrid = new ArrayList<>();

        Coord cityCenter = new Coord(55.751244, 37.618423);

        GeodeticCalculator calc = new GeodeticCalculator();
        //Размер отступа между гексагонами - равен перпендикуляру от центра до стороны гексагона
        double offset = Math.sqrt(Math.pow(hexagonRadius, 2) - Math.pow(hexagonRadius / 2, 2));

        //Генерим N*M точек с центрами гексагонов
        //Для каждой точки вычисляем координаты гексагона
        int n = 30;
        int m = 50;

        for (int i = -m; i <= m; i++) {
            //Вычисление центрального гексагона по оси Y
            Point2D centerPoint;
            //Вычисление координат центрального гексагона для четного и нечетного ряда
            if (i % 2 == 0) {
                calc.setStartingGeographicPoint(cityCenter.getLongitude(), cityCenter.getLatitude());
                double offsetY = 3 * hexagonRadius;
                calc.setDirection(0, i / 2 * offsetY);
            } else {
                calc.setStartingGeographicPoint(cityCenter.getLongitude(), cityCenter.getLatitude());
                calc.setDirection(30, offset * 2);
                centerPoint = calc.getDestinationGeographicPoint();
                calc.setStartingGeographicPoint(centerPoint);
                double offsetY = 3 * hexagonRadius;
                calc.setDirection(0, (i-1)/2 * offsetY);
            }
            centerPoint = calc.getDestinationGeographicPoint();

            for (int j = -n; j <= n; j++) {
                //Вычисление центров гексагонов по оси X
                calc.setStartingGeographicPoint(centerPoint.getX(), centerPoint.getY());
                calc.setDirection(90, j * offset * 2);
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
        }

        return hexGrid;
    }

}

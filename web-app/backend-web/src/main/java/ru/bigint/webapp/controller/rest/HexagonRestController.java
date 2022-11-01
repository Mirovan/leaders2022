package ru.bigint.webapp.controller.rest;

import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bigint.webapp.repository.postgis.hexgrid.HexagonalGrid;

import java.util.List;

@RestController
@RequestMapping(value = "/api/hexagon")
public class HexagonRestController {

    private final HexagonalGrid hexagonalGrid;

    public HexagonRestController(HexagonalGrid hexagonalGrid) {
        this.hexagonalGrid = hexagonalGrid;
    }

    @GetMapping()
    public List<String> generate(@RequestParam(value = "hexagonRadius", defaultValue = "300") Double hexagonRadius)
            throws FactoryException, TransformException {
        return hexagonalGrid.getHexagonalGrid(hexagonRadius);
    }

}

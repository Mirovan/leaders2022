package ru.bigint.webapp.controller.rest;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bigint.webapp.dto.ColoredPolygon;
import ru.bigint.webapp.repository.postgis.hexgrid.HexagonalGrid;
import ru.bigint.webapp.service.iface.CalcService;

import java.util.List;

@Api(tags = { "Гексагоны" })
@Tag(name = "Гексагоны", description = "Расчет гексагональной тепловой карты")
@RestController
@RequestMapping(value = "/api/calc")
public class CalcRestController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final CalcService calcService;
    private final HexagonalGrid hexagonalGrid;

    public CalcRestController(CalcService calcService, HexagonalGrid hexagonalGrid) {
        this.calcService = calcService;
        this.hexagonalGrid = hexagonalGrid;
    }

    @Operation(summary = "Расчет гексагональной тепловой карты")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping()
    public List<ColoredPolygon> calculate(
            @RequestParam(value = "radius") int radius,
            @RequestParam(value = "kmlId") String kmlId,
            @RequestParam(value = "considerHouses") boolean considerHouses,
            @RequestParam(value = "considerMalls") boolean considerMalls,
            @RequestParam(value = "considerSupermarkets") boolean considerSupermarkets,
            @RequestParam(value = "considerMetro") boolean considerMetro,
            @RequestParam(value = "considerWorkCenter") boolean considerWorkCenter,
            @RequestParam(value = "considerChildHouse") boolean considerChildHouse,
            @RequestParam(value = "considerParking") boolean considerParking,
            @RequestParam(value = "considerPostamat") boolean considerPostamat
            ) {
        return calcService.calculate(radius, kmlId, considerHouses, considerMalls, considerSupermarkets, considerMetro,
                considerPostamat, considerWorkCenter, considerChildHouse, considerParking);
    }

    @Operation(summary = "Расчет координат секторов гексагональной карты")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/hexagon-map")
    public List<String> generate(@RequestParam(value = "hexagonRadius", defaultValue = "300") Double hexagonRadius)
            throws FactoryException, TransformException {
        return hexagonalGrid.getHexagonalGrid(hexagonRadius);
    }
}

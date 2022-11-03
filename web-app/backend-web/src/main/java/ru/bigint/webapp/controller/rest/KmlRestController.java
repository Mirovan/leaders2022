package ru.bigint.webapp.controller.rest;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bigint.webapp.service.iface.KmlJobService;

@Api(tags = { "Служебные слои" })
@Tag(name = "Служебные слои", description = "Сервис создания задач для расчета слоев")
@RestController
@RequestMapping(value = "/api/jobs")
public class KmlRestController {

    private final KmlJobService kmlJobService;

    public KmlRestController(KmlJobService kmlJobService) {
        this.kmlJobService = kmlJobService;
    }

    @Operation(summary = "Тепловая карта на основе плотности населения")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Возвращает тепловую карта на основе плотности населения",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/{id}.txt")
    @ResponseBody
    public ResponseEntity<byte[]> kml(@PathVariable String id) {
        byte[] result = kmlJobService.getKmlResultById(id);
        return ResponseEntity.ok()
                .header("Content-Type", "text/plain")
                .body(result);
    }

}

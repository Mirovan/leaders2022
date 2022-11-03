package ru.bigint.webapp.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bigint.webapp.dto.AnalyticsDto;
import ru.bigint.webapp.entity.Postamat;
import ru.bigint.webapp.service.iface.AnalyticsService;
import ru.bigint.webapp.service.iface.PostamatService;

import java.util.List;

@Tag(name = "analytics", description = "Работа с анатическими данными и статистикой")
@RestController
@RequestMapping(value = "/api/analytics")
public class AnalyticsRestController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PostamatService postamatService;
    private final AnalyticsService analyticsService;

    public AnalyticsRestController(PostamatService postamatService, AnalyticsService analyticsService) {
        this.postamatService = postamatService;
        this.analyticsService = analyticsService;
    }

    @Operation(summary = "Получение статистики по всем постаматам")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Возвращает стаститику использования постаматов",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/postamat/using/all")
    public List<AnalyticsDto> getPostamatUsing() {
        return analyticsService.getUsingStat();
    }

    @Operation(summary = "Получение статистики по загрузке постамата")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Возвращает стаститику по загрузке постамата",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/postamat/using")
    public Postamat getPostamatUsing(@RequestParam int id) {
        return postamatService.getById(id);
    }

}

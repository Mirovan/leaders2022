package ru.bigint.webapp.controller.rest;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.LibraryDto;
import ru.bigint.webapp.service.iface.LibraryService;

import java.util.List;

@Api(tags = { "Библиотеки" })
@Tag(name = "Библиотеки", description = "Работа с данными библиотек")
@RestController
@RequestMapping(value = "/api/library")
public class LibraryRestController {

    private final LibraryService libraryService;

    public LibraryRestController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/nearest")
    public List<LibraryDto> getNearestMalls(@RequestParam(value = "latitude", required = false) Double latitude,
                                            @RequestParam(value = "longitude", required = false) Double longitude) {
        return libraryService.findNearest(new Coord(latitude, longitude), 3);
    }

}

package ru.bigint.webapp.service.iface;

import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.LibraryDto;

import java.util.List;

public interface LibraryService {
    List<LibraryDto> findNearest(Coord point, int count);
}

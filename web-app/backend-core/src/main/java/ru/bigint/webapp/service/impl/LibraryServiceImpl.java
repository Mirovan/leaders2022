package ru.bigint.webapp.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.LibraryDto;
import ru.bigint.webapp.repository.LibraryRepo;
import ru.bigint.webapp.service.iface.LibraryService;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {
    private final LibraryRepo libraryRepo;

    public LibraryServiceImpl(LibraryRepo libraryRepo) {
        this.libraryRepo = libraryRepo;
    }

    @Override
    public List<LibraryDto> findNearest(Coord point, int count) {
        return libraryRepo.findNearest(point, Pageable.ofSize(count));
    }
}

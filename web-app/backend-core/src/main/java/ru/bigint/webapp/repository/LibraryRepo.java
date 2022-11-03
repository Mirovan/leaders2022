package ru.bigint.webapp.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.LibraryDto;
import ru.bigint.webapp.dto.MfcDto;
import ru.bigint.webapp.entity.Library;
import ru.bigint.webapp.entity.Mfc;

import java.util.List;

@Repository
public interface LibraryRepo extends JpaRepository<Library, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT library.*, ST_Distance( " +
                    "  ST_MakePoint(library.latitude, library.longitude), ST_MakePoint(:#{#point.latitude}, :#{#point.longitude}) " +
                    "  ) AS dist " +
                    " FROM " +
                    "  library " +
                    " ORDER BY " +
                    "  dist ")
    List<LibraryDto> findNearest(@Param("point") Coord point, Pageable pageable);
}
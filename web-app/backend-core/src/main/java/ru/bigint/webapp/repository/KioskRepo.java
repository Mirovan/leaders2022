package ru.bigint.webapp.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.KioskDto;
import ru.bigint.webapp.entity.Kiosk;

import java.util.List;

@Repository
public interface KioskRepo extends JpaRepository<Kiosk, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT kiosk.*, ST_Distance( " +
                    "  ST_MakePoint(kiosk.latitude, kiosk.longitude), ST_MakePoint(:#{#point.latitude}, :#{#point.longitude}) " +
                    "  ) AS dist " +
                    " FROM " +
                    "  kiosk " +
                    " ORDER BY " +
                    "  dist ")
    List<KioskDto> findNearest(@Param("point") Coord point, Pageable pageable);
}
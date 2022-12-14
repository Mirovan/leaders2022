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
            value = "SELECT k.id, k.name, k.address, k.adm_area AS \"admArea\", k.business_entity AS \"businessEntity\", k.latitude, k.longitude, ST_Distance( " +
                    "  ST_MakePoint(k.latitude, k.longitude), ST_MakePoint(:#{#point.latitude}, :#{#point.longitude}) " +
                    "  ) AS dist " +
                    " FROM " +
                    "  kiosk k " +
                    " ORDER BY " +
                    "  dist ")
    List<KioskDto> findNearest(@Param("point") Coord point, Pageable pageable);
}
package ru.bigint.webapp.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.MfcDto;
import ru.bigint.webapp.entity.Mfc;

import java.util.List;

@Repository
public interface MfcRepo extends JpaRepository<Mfc, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT mfc.*, ST_Distance( " +
                    "  ST_MakePoint(mfc.latitude, mfc.longitude), ST_MakePoint(:#{#point.latitude}, :#{#point.longitude}) " +
                    "  ) AS dist " +
                    " FROM " +
                    "  mfc " +
                    " ORDER BY " +
                    "  dist ")
    List<MfcDto> findNearest(@Param("point") Coord point, Pageable pageable);
}
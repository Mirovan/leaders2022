package ru.bigint.webapp.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.MallDto;
import ru.bigint.webapp.entity.Mall;

import java.util.List;

@Repository
public interface MallRepo extends JpaRepository<Mall, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT malls.id, malls.name, malls.address, malls.about, malls.phone, malls.latitude, malls.longitude, ST_Distance( " +
                    "  ST_MakePoint(malls.latitude, malls.longitude), ST_MakePoint(:#{#point.latitude}, :#{#point.longitude}) " +
                    "  ) AS dist " +
                    " FROM " +
                    "  malls " +
                    " ORDER BY " +
                    "  dist ")
    List<MallDto> findNearest(@Param("point") Coord point, Pageable pageable);
}
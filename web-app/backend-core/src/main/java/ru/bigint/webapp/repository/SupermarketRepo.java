package ru.bigint.webapp.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bigint.webapp.dto.Coord;
import ru.bigint.webapp.dto.SupermarketDto;
import ru.bigint.webapp.entity.Supermarket;

import java.util.List;

@Repository
public interface SupermarketRepo extends JpaRepository<Supermarket, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT supermarkets.id, supermarkets.name, supermarkets.address, supermarkets.about, supermarkets.phone, supermarkets.latitude, supermarkets.longitude, ST_Distance( " +
                    "  ST_MakePoint(supermarkets.latitude, supermarkets.longitude), ST_MakePoint(:#{#point.latitude}, :#{#point.longitude}) " +
                    "  ) AS dist " +
                    " FROM " +
                    "  supermarkets " +
                    " ORDER BY " +
                    "  dist ")
    List<SupermarketDto> findNearest(@Param("point") Coord point, Pageable pageable);
}
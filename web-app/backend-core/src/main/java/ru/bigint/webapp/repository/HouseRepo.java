package ru.bigint.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bigint.webapp.entity.House;

@Repository
public interface HouseRepo extends JpaRepository<House, Integer> {
}
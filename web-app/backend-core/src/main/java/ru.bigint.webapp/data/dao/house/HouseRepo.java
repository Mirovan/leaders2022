package ru.bigint.webapp.data.dao.house;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bigint.webapp.data.entity.House;
import ru.bigint.webapp.data.entity.user.User;

@Repository
public interface HouseRepo extends JpaRepository<House, Integer> {
}
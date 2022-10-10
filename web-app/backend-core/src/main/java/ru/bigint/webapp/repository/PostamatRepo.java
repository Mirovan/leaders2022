package ru.bigint.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bigint.webapp.entity.Postamat;

@Repository
public interface PostamatRepo extends JpaRepository<Postamat, Integer> {
}
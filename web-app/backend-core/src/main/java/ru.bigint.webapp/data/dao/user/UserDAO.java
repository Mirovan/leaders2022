package ru.bigint.webapp.data.dao.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bigint.webapp.data.entity.user.User;

@Repository("userDAO")
public interface UserDAO extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
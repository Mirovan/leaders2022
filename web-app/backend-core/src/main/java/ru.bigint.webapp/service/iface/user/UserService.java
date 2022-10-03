package ru.bigint.webapp.service.iface.user;

import ru.bigint.webapp.data.entity.user.User;

import java.util.List;

public interface UserService {
    void add(User user);
    void update(User user, User updateUser);
    User getById(Integer id);
    User getByEmail(String email);
    List<User> getAll();
    List<User> getAllByOrder(String order);
    String generateRandomPassword();            //Генерация случайного пароля
    String encodePassword(String password);     //Шифрование пароля
}

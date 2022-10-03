package ru.bigint.webapp.service.impl.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.bigint.webapp.data.dao.user.UserDAO;
import ru.bigint.webapp.data.entity.user.User;
import ru.bigint.webapp.service.iface.user.UserService;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final UserDAO userDAO;

    public UserServiceImpl(@Qualifier("userDAO") UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void add(User user) {
        userDAO.save(user);
    }


    @Override
    public void update(User user, User updateUser) {
        if (updateUser.getPassword() != null && !updateUser.getPassword().trim().equals(""))
            user.setPassword( encodePassword(updateUser.getPassword()) );
        user.setName( updateUser.getName() );
        user.setEmail( updateUser.getEmail() );
        user.setRoles( updateUser.getRoles() );
        user.setActive( updateUser.getActive() );

        userDAO.save(user);
    }


    @Override
    public User getById(Integer id) {
        return userDAO.findById(id).get();
    }


    @Override
    public User getByEmail(String email) {
        User user = userDAO.findByEmail(email);
        return user;
    }


    @Override
    public List<User> getAll() {
        return userDAO.findAll();
    }


    @Override
    public List<User> getAllByOrder(String order) {
        if ( "ASC".equals(order) )
            return userDAO.findAll(Sort.by(Sort.Direction.ASC, "id"));
        else
            return userDAO.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }


    @Override
    public String generateRandomPassword() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));
    }


    @Override
    public String encodePassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

}

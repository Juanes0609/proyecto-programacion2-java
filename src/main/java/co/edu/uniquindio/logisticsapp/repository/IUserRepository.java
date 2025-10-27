package co.edu.uniquindio.logisticsapp.repository;

import co.edu.uniquindio.logisticsapp.model.User;
import java.util.List;

public interface IUserRepository {
    void saveUser(User user);
    List<User> findAllUsers();
    User findUserByEmail(String email);
}

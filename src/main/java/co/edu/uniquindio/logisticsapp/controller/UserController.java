package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.User;
import co.edu.uniquindio.logisticsapp.service.LogisticsServiceImpl;
import javafx.event.ActionEvent;

import java.util.List;

public class UserController {
    private final LogisticsServiceImpl service = new LogisticsServiceImpl();

    public void registerUser (String fullName, String email, String phone) { 
        User newUser = new User(fullName, email, phone);
        service.registerUser(newUser);
        System.out.println("Usuario registrado correctamente: " + fullName);
    }

    public List<User> getAllUsers () { 
        return service.getRepository().getUsers();
    }

    public User findUserByEmail (String email) {
        return service.getRepository().getUsers()
                              .stream()
                              .filter(u -> u.getEmail().equalsIgnoreCase(email))
                              .findFirst()
                              .orElse(null);

    }
}



package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.User;
import co.edu.uniquindio.logisticsapp.service.LogisticsServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class UserController {

    private Button btnLogout;

    @FXML
    private StackPane contentArea;

    @FXML
    private BorderPane mainContainer;

    @FXML
    private VBox sideBar;


    public void onLogout(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login");
    }
    
    private final LogisticsServiceImpl service = new LogisticsServiceImpl();

    public void registerUser (String fullName, String email, String phone) { 
        User newUser = new User(fullName, email, phone);
        service.registerUser(newUser);
        System.out.println("Usuario registrado correctamente: " + fullName);
    }

    public List<User> getAllUsers () { 
        return service.getRepository().getUserList();
    }

    public User findUserByEmail (String email) {
        return service.getRepository().getUserList()
                              .stream()
                              .filter(u -> u.getEmail().equalsIgnoreCase(email))
                              .findFirst()
                              .orElse(null);

    }
}


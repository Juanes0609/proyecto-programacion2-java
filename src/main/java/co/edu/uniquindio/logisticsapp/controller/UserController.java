package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.User;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import co.edu.uniquindio.logisticsapp.service.LogisticsServiceImpl;
import co.edu.uniquindio.logisticsapp.controller.UserAdressesController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class UserController {

    @FXML
    private Button btnLogout;

    @FXML
    private StackPane contentArea;

    @FXML
    private BorderPane mainContainer;

    @FXML
    private VBox sideBar;

    private String userEmail;
    private User currentUser;
    private  LogisticsRepository repository = LogisticsRepository.getInstance();

    public void onLogout(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login");
    }

    public void onProfile(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserProfile.fxml"));
            Parent view = loader.load();

            UserProfileController controller  = loader.getController();
            controller.setUserEmail(userEmail);
            controller.setDashboardUserController(this);

            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setUserEmail(String email) {
        this.userEmail = email;
        System.out.println("Usuario logueado: " + email);
    }

    @FXML
    private void onGoToDashboard() {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(new Label("Bienvenido al sistema de gestión de Usuario"));
    }
    void backToDashboard() {
        onGoToDashboard();
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

    public void onAddressesClick(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserAddresses.fxml"));
            Parent view = loader.load();

            UserAdressesController controller = loader.getController();
            controller.setUser(repository.getUserByEmail(userEmail));

            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void onShipment(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserShipment.fxml"));
            Parent view = loader.load();

            UserShipmentController controller = loader.getController();
            User user = repository.getUserByEmail(userEmail);
            this.currentUser = user;
            controller.setCurrentUser(user);
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onShipmentList(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserShipmentList.fxml"));
            Parent view = loader.load();

            UserShipmentListController controller = loader.getController();


            if (currentUser == null) {
                currentUser = repository.getUserByEmail(userEmail);
            }

            controller.loadShipment(currentUser);

            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


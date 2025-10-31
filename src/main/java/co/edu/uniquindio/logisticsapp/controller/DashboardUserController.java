package co.edu.uniquindio.logisticsapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardUserController {
    private String userEmail;
    @FXML
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
}




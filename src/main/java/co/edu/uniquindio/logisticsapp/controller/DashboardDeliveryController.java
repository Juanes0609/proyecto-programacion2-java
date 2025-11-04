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

public class DashboardDeliveryController {
    @FXML
    private Button btnLogout;

    @FXML
    private StackPane contentArea;

    @FXML
    private BorderPane mainContainer;

    @FXML
    private VBox sideBar;
    private String deliveryEmail;

    public void onLogout(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login");
    }

    public void onProfile(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DeliveryProfile.fxml"));
            Parent view = loader.load();

            DeliveryProfileController controller  = loader.getController();
            controller.setDeliveryEmail(deliveryEmail);
            controller.setDashboardDeliveryController(this);

            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void onGoToDashboard() {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(new Label("Bienvenido al sistema de gestión de Repartidor"));
    }
    void backToDashboard() {
        onGoToDashboard();
    }

    public void setDeliveryEmail(String email) {
        this.deliveryEmail = email;
        System.out.println("📦 Email del repartidor recibido en Dashboard: " + email);
    }
}

/*
package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardAdminController {
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

    public void onGoToList(ActionEvent actionEvent){ loadView("/UsersList.fxml", "listado");}
    public void onAddUser(ActionEvent actionEvent) { loadView("/Register.fxml", "registro");}
    public void onGoToListDelivery(ActionEvent actionEvent) { loadView("/DeliveryList.fxml", "listadoDeliveries");}


        private void loadView(String fxmlPath, String typeView) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent view = loader.load();

                contentArea.getChildren().clear();
                contentArea.getChildren().add(view);

                switch (typeView) {
                    case "listado" -> {
                        UsersListController controller = loader.getController();
                        controller.setDashboardAdminController(this);
                        controller.loadUsers();
                    }
                    case "registro" -> {
                        RegisterController controller = loader.getController();
                        controller.setDashboardAdminController(this);
                    }
                    case "listadoDeliveries" -> {
                        DeliveryListController controller = loader.getController();
                        controller.setDashboardAdminController(this);
                    }
                }

            } catch (IOException e) {
                showAlert("Error", "No se pudo cargar la vista: " + typeView, Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    @FXML
    private void onGoToDashboard() {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(new Label("Bienvenido al sistema de gestión de Administrador"));
    }
    void backToDashboard() {
        onGoToDashboard();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alerta = new Alert(type);
        alerta.setTitle(title);
        alerta.setHeaderText(null);
        alerta.setContentText(message);
        alerta.showAndWait();
    }
}
*/






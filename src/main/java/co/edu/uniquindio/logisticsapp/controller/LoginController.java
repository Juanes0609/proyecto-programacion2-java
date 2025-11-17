package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.User;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Label lblResultado;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField pwdPin;

    private final LogisticsRepository repository = LogisticsRepository.getInstance();

    @FXML
    void onLoginClick(ActionEvent event) {
        String email = txtEmail.getText().trim();
        String pin = pwdPin.getText();

        if (email.isEmpty() || pin.isEmpty()) {
            lblResultado.setText("⚠ Ingrese correo y PIN.");
            lblResultado.setStyle("-fx-text-fill: orange;");
            return;
        }

        User loggedUser = repository.loginUser(email, pin);
        Delivery loggedDelivery = repository.loginDelivery(email, pin);

        boolean loginExitoso = (loggedUser != null || loggedDelivery != null);

        if (!loginExitoso) {
            lblResultado.setText("❌ Credenciales inválidas o usuario no registrado.");
            lblResultado.setStyle("-fx-text-fill: red;");
            return;
        }

        FXMLLoader loader = null;
        Scene scene = null;
        String title = "Panel principal";

        try {
            String lowerEmail = email.toLowerCase();

            if (loggedUser != null && lowerEmail.contains("admin")) {
                lblResultado.setText("✅ Bienvenido Administrador");
                lblResultado.setStyle("-fx-text-fill: green;");
                loader = new FXMLLoader(getClass().getResource("/DashboardAdmin.fxml"));
                title = "Panel Admin";

            } else if (loggedDelivery != null && lowerEmail.contains("delivery")) {
                lblResultado.setText("✅ Bienvenido Repartidor");
                lblResultado.setStyle("-fx-text-fill: green;");
                loader = new FXMLLoader(getClass().getResource("/DashboardDelivery.fxml"));
                title = "Panel Repartidor";

            } else if (loggedUser != null) {
                lblResultado.setText("👋 Bienvenido Usuario");
                lblResultado.setStyle("-fx-text-fill: blue;");
                loader = new FXMLLoader(getClass().getResource("/DashboardUser.fxml"));
                title = "Panel Usuario";

            } else {

                lblResultado.setText("❌ Acceso denegado. Rol no asignado al email.");
                lblResultado.setStyle("-fx-text-fill: red;");
                return;
            }

            if (loader != null) {
                scene = new Scene(loader.load());

                Object controller = loader.getController();

                if (controller instanceof DashboardDeliveryController deliveryController) {

                    deliveryController.setDeliveryEmail(email);
                    deliveryController.setCurrentDelivery(loggedDelivery);

                } else if (controller instanceof UserController userController) {

                    userController.setUserEmail(email);
                }

                if (scene != null) {

                    Stage stage = (Stage) txtEmail.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle(title);
                    stage.show();

                    javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();

                    stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);

                    stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

                    stage.show();

                }

            }

        } catch (Exception e) {
            e.printStackTrace();

            lblResultado.setText("⚠ Error fatal al cargar la vista. Revise la consola para la traza de FXML.");
            lblResultado.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    void onRegisterClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/register.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) txtEmail.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Registrar Usuario");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
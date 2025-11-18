package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.User;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class RegisterController {

    @FXML
    private TextField txtFullName, txtEmail, txtPhone;

    @FXML
    private PasswordField pwdPin;

    @FXML
    private Label lblMensaje;

    private final LogisticsRepository repository = LogisticsRepository.getInstance();

    private AdminController adminController;

    @FXML
    public void onSaveClick() {

        String fullName = txtFullName.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String pin = pwdPin.getText().trim();

        if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || pin.isEmpty()) {
            lblMensaje.setText("Debe llenar todos los campos ⚠️");
            lblMensaje.setStyle("-fx-text-fill: orange;");
            return;
        }

        if (pin.length() != 4 || !pin.matches("\\d+")) {
            lblMensaje.setText("⚠ El PIN debe ser numérico y tener 4 dígitos.");
            lblMensaje.setStyle("-fx-text-fill: orange;");
            return;
        }

        String lowerEmail = email.toLowerCase();

        if (repository.existsUser(lowerEmail)
                || repository.getDeliveriesList().stream().anyMatch(d -> d.getEmail().equalsIgnoreCase(lowerEmail))) {
            lblMensaje.setText("El correo ya está registrado ❌");
            lblMensaje.setStyle("-fx-text-fill: red;");
            return;
        }

        if (lowerEmail.contains("delivery")) {

            Delivery delivery = new Delivery.Builder()
                    .fullName(fullName)
                    .email(lowerEmail)
                    .phone(phone)
                    .pin(pin)
                    .build();

            repository.addDelivery(delivery);

            lblMensaje.setText("Repartidor registrado con éxito ✅");
            lblMensaje.setStyle("-fx-text-fill: green;");

            txtFullName.clear();
            txtEmail.clear();
            txtPhone.clear();
            pwdPin.clear();

        } else if (lowerEmail.contains("admin")) {
            User newUser = new User(fullName, lowerEmail, phone, pin);
            repository.addUser(newUser);

            lblMensaje.setText("Admin registrado con éxito ✅");
            lblMensaje.setStyle("-fx-text-fill: green;");

        } else {
            User newUser = new User(fullName, lowerEmail, phone, pin);
            repository.addUser(newUser);
            repository.ensureUserIsBankClient(newUser);

            lblMensaje.setText("Usuario registrado con éxito ✅");
            lblMensaje.setStyle("-fx-text-fill: green;");
        }
    }

    @FXML
    public void onBackClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) txtFullName.getScene().getWindow();

            javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
            stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
            stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();

        } catch (Exception e) {
            System.err.println("Error al volver a Login:");
            e.printStackTrace();
        }
    }

    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }
}
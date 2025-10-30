package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.service.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Label lblResultado;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private ComboBox<String> cbRole;

    private final LoginService loginService = new LoginService();

    @FXML
    public void initialize() {
        // Inicializar combo box de roles
        cbRole.getItems().addAll("USUARIO", "ADMINISTRADOR", "REPARTIDOR");
    }

    @FXML
    void onLoginClick(ActionEvent event) {
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();
        String role = cbRole.getValue();

        if (email.isEmpty() || password.isEmpty() || role == null) {
            lblResultado.setText("⚠ Complete todos los campos");
            lblResultado.setStyle("-fx-text-fill: orange;");
            return;
        }

        boolean loginExitoso = loginService.login(email, password, role);

        if (loginExitoso) {
            lblResultado.setText("✅ Bienvenido " + role);
            lblResultado.setStyle("-fx-text-fill: green;");
            // redirigir a vista según rol
        } else {
            lblResultado.setText("❌ Credenciales inválidas");
            lblResultado.setStyle("-fx-text-fill: red;");
        }
    }
}

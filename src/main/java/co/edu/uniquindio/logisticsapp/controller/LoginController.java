package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.service.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Label lblResultado;

    @FXML
    private TextField txtEmail;

    private final LoginService loginService = new LoginService();

    @FXML
    void onLoginClick(ActionEvent event) {

        String email = txtEmail.getText().trim();

        if(email.isEmpty()){
            lblResultado.setText("⚠ Ingrese un correo electrónico");
            lblResultado.setStyle("-fx-text-fill: orange;");
            return;
        }

        boolean loginExitoso = loginService.login(email);

    if(loginExitoso){
        if (email.toLowerCase().contains("admin")) {
            lblResultado.setText("✅ Bienvenido Administrador");
            lblResultado.setStyle("-fx-text-fill: green;");
        } else {
            lblResultado.setText("👋 Bienvenido Usuario");
            lblResultado.setStyle("-fx-text-fill: blue;");
        }
    }else{
            lblResultado.setText("❌ Usuario no registrado");
            lblResultado.setStyle("-fx-text-fill: red;");
        }
    }
}


package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.service.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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



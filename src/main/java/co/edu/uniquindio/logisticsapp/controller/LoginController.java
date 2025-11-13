package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
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

        if (email.isEmpty()) {
            lblResultado.setText("⚠ Ingrese un correo electrónico");
            lblResultado.setStyle("-fx-text-fill: orange;");
            return;
        }

        boolean loginExitoso = loginService.loginUser(email);
        boolean loginExitosoDel = loginService.loginDelivery(email);

        if (loginExitoso || loginExitosoDel) {
            try {
                FXMLLoader loader;
                Scene scene;

                if (email.toLowerCase().contains("admin")) {
                    lblResultado.setText("✅ Bienvenido Administrador");
                    lblResultado.setStyle("-fx-text-fill: green;");
                    loader = new FXMLLoader(getClass().getResource("/DashboardAdmin.fxml"));
                    scene = new Scene(loader.load());

                } else if (email.toLowerCase().contains("delivery")) {
                    lblResultado.setText("✅ Bienvenido Repartidor");
                    lblResultado.setStyle("-fx-text-fill: green;");
                    loader = new FXMLLoader(getClass().getResource("/DashboardDelivery.fxml"));
                     scene = new Scene(loader.load());

                     DashboardDeliveryController controller = loader.getController();
                     controller.setDeliveryEmail(email);
                    Delivery delivery = LogisticsRepository.getInstance().getDeliveryByEmail(email);
                    controller.setCurrentDelivery(delivery);

                } else {
                    lblResultado.setText("👋 Bienvenido Usuario");
                    lblResultado.setStyle("-fx-text-fill: blue;");
                    loader = new FXMLLoader(getClass().getResource("/DashboardUser.fxml"));
                    scene = new Scene(loader.load());


                    Object controller = loader.getController();
                    if (controller instanceof UserController userController) {
                        userController.setUserEmail(email);
                    }
                }

                // 🔹 Mostrar el dashboard
                Stage stage = (Stage) txtEmail.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Panel principal");
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
                lblResultado.setText("⚠ Error al cargar la vista");
                lblResultado.setStyle("-fx-text-fill: red;");
            }
        } else {
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
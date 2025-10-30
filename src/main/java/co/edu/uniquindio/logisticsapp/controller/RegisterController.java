package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.User;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

public class RegisterController {
    private DashboardAdminController dashboardAdminController;

    @FXML
    private TextField txtFullName,txtEmail,txtPhone;

    @FXML
    private Label lblMensaje;

    private final LogisticsRepository repository = LogisticsRepository.getInstance();

    @FXML
    public void onSaveClick() {
        String fullName = txtFullName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();

        if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            lblMensaje.setText("Debe llenar todos los campos ⚠️");
            return;
        }

        if (repository.existsUser(email)) {
            lblMensaje.setText("El correo ya está registrado ❌");
            return;
        }else if(email.toLowerCase().contains("delivery")){
            //Aqui va la creacion del delivery
            System.out.println("Repartidor agregado");
            lblMensaje.setText("Repartidor agregado");
        }else if(email.toLowerCase().contains("admin")){
            User newUser = new User(fullName, email, phone);
            repository.addUser(newUser);

            lblMensaje.setText("Admin registrado con éxito ✅");
        }else{
            User newUser = new User(fullName, email, phone);
            repository.addUser(newUser);

            lblMensaje.setText("usuario registrado con éxito ✅");
        }
    }

    @FXML
    public void onBackClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) txtFullName.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDashboardAdminController(DashboardAdminController dashboardAdminController) {
        this.dashboardAdminController = dashboardAdminController;
    }
}


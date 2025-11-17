package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DeliveryProfileController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnSave;

    @FXML
    private Label lblMessage;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtPhone;

    private Delivery currentDelivery;
    final private LogisticsRepository repository = LogisticsRepository.getInstance();
    private DashboardDeliveryController dashboardDeliveryController;

    public void setDelivery(Delivery delivery) {
        this.currentDelivery = delivery;
        loadDeliveryData();
    }

    public void loadDeliveryData(){
        if(currentDelivery != null){
            txtFullName.setText(currentDelivery.getFullName());
            txtPhone.setText(currentDelivery.getPhone());
            txtEmail.setText(currentDelivery.getEmail());

        }
    }

    @FXML
    void onBackToDashboard(ActionEvent event) {
        if (dashboardDeliveryController != null) {
            dashboardDeliveryController.backToDashboard();
        }
    }

    public void setDeliveryEmail(String email) {
        this.currentDelivery = repository.getDeliveryByEmail(email);
        if (currentDelivery != null) {
            loadDeliveryData();
        } else {
            System.out.println("⚠️ No se encontró delivery con el email: " + email);
        }
    }

    public void setDashboardDeliveryController(DashboardDeliveryController dashboardDeliveryController) {
        this.dashboardDeliveryController = dashboardDeliveryController;
    }

    @FXML
    void onEditClick(ActionEvent event) {
        txtFullName.setEditable(true);
        txtPhone.setEditable(true);
        btnSave.setDisable(false);
        lblMessage.setText("Puedes editar tu nombre y teléfono.");
    }

    @FXML
    void onSaveClick(ActionEvent event) {
        String fullName =  txtFullName.getText().trim();
        String phone = txtPhone.getText().trim();

        if(fullName.isEmpty() || phone.isEmpty()){
            lblMessage.setText("Por favor ingrese todos los datos.");
            return;
        }

        currentDelivery.setFullName(fullName);
        currentDelivery.setPhone(phone);
        repository.updateDelivery(currentDelivery);

        txtFullName.setEditable(false);
        txtPhone.setEditable(false);
        btnSave.setDisable(true);

        lblMessage.setText("Informacion actualizada correctamente.");
    }

}
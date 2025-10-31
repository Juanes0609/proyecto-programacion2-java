package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.User;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserProfileController {
    public Button btnBack;
    @FXML
    private TextField txtFullName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhone;
    @FXML
    private Label lblMensaje;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnSave;
    private DashboardUserController dashboardUserController;

    private User currentUser;
    private final LogisticsRepository repository = LogisticsRepository.getInstance();

    public void setUser(User user) {
        this.currentUser = user;
        loadUserData();
    }

    private void loadUserData() {
        if (currentUser != null) {
            txtFullName.setText(currentUser.getFullName());
            txtEmail.setText(currentUser.getEmail());
            txtPhone.setText(currentUser.getPhone());
        }
    }

    @FXML
    private void onEditClick() {
        txtFullName.setEditable(true);
        txtPhone.setEditable(true);
        btnSave.setDisable(false);
        lblMensaje.setText("✏️ Puedes editar tu nombre y teléfono.");
    }

    @FXML
    private void onSaveClick() {
        String fullName = txtFullName.getText().trim();
        String phone = txtPhone.getText().trim();

        if (fullName.isEmpty() || phone.isEmpty()) {
            lblMensaje.setText("⚠️ Todos los campos son obligatorios.");
            return;
        }

        // Actualizar usuario
        currentUser.setFullName(fullName);
        currentUser.setPhone(phone);
        repository.updateUser(currentUser);

        txtFullName.setEditable(false);
        txtPhone.setEditable(false);
        btnSave.setDisable(true);

        lblMensaje.setText("✅ Información actualizada correctamente.");
    }

    public void setUserEmail(String email) {
        this.currentUser = repository.getUserByEmail(email);
        loadUserData();
    }


    public void setDashboardUserController(DashboardUserController dashboardUserController) {
        this.dashboardUserController = dashboardUserController;
    }

    @FXML
    private void onBackToDashboard() {
        if (dashboardUserController != null) {
            dashboardUserController.backToDashboard();
        }
    }
}


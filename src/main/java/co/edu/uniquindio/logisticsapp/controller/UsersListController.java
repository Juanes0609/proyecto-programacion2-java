package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.User;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import co.edu.uniquindio.logisticsapp.service.LogisticsServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.UUID;

public class UsersListController {
    public Button btnBack;
    public Button btnDelete;

    @FXML
        private TableView<User> tablaUsers;
        @FXML private TableColumn<User, UUID> colId;
        @FXML private TableColumn<User, String> colName;
        @FXML private TableColumn<User, String> colEmail;
        @FXML private TableColumn<User, String> colPhone;

        private AdminController adminController;
        private ObservableList<User> usersList;
        private LogisticsRepository logisticsRepository;
        private LogisticsServiceImpl logisticsServiceImpl;

        @FXML
        public void initialize() {
            colId.setCellValueFactory(new PropertyValueFactory<>("userId"));
            colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            logisticsRepository = LogisticsRepository.getInstance();
            logisticsServiceImpl = new LogisticsServiceImpl();
            loadUsers();
        }

        public void loadUsers() {
            usersList = FXCollections.observableList
                    (logisticsServiceImpl.getAllUsers());
            tablaUsers.setItems(usersList);
        }
    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }

        @FXML
        private void onBackToDashboard() {
            if (adminController != null) {
                adminController.backToDashboard();
            }
        }



    @FXML
    void onDeleteButton(ActionEvent event) {
        User selected = tablaUsers.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Advertencia", "Seleccione un usuario para eliminar", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Eliminar este usuario?", ButtonType.OK, ButtonType.CANCEL);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                logisticsRepository.deleteUser(selected);
                loadUsers();
                showAlert("Éxito", "Usuario eliminado", Alert.AlertType.INFORMATION);
            }
        });
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alerta = new Alert(type);
        alerta.setTitle(title);
        alerta.setHeaderText(null);
        alerta.setContentText(message);
        alerta.showAndWait();
    }
}

package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.User;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import co.edu.uniquindio.logisticsapp.service.LogisticsServiceImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.UUID;

public class DeliveryListController {
    public Button btnBack;
    public Button btnDelete;

    @FXML

    private TableView<Delivery> tablaDeliveries;
    @FXML
    private TableColumn<Delivery, UUID> colId;
    @FXML
    private TableColumn<Delivery, String> colName;
    @FXML
    private TableColumn<Delivery, String> colEmail;
    @FXML
    private TableColumn<Delivery, String> colPhone;

    private AdminController adminController;
    private ObservableList<Delivery> deliveryList;
    private LogisticsRepository logisticsRepository;
    private LogisticsServiceImpl logisticsServiceImpl;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        logisticsRepository = LogisticsRepository.getInstance();
        logisticsServiceImpl = new LogisticsServiceImpl();
        loadDeliveries();
    }

    public void loadDeliveries() {
        deliveryList = FXCollections.observableList(logisticsServiceImpl.getAllDeliveries());
        tablaDeliveries.setItems(deliveryList);
    }

    public void setadminController(AdminController adminController) {
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
        Delivery selected = tablaDeliveries.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Advertencia", "Seleccione un Repartidor para eliminar", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Eliminar este Repartidor?", ButtonType.OK,
                ButtonType.CANCEL);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                logisticsRepository.deleteDelivery(selected);
                loadDeliveries();
                showAlert("Éxito", "Repartidor eliminado", Alert.AlertType.INFORMATION);
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

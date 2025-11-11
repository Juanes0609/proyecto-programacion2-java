package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.dto.DeliveryDTO;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import co.edu.uniquindio.logisticsapp.service.LogisticsServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class DeliveryListController {
    public Button btnBack;
    public Button btnDelete;

    @FXML

    private TableView<DeliveryDTO> tablaDeliveries;
    @FXML
    private TableColumn<DeliveryDTO, String> colId;
    @FXML
    private TableColumn<DeliveryDTO, String> colName;
    @FXML
    private TableColumn<DeliveryDTO, String> colEmail;
    @FXML
    private TableColumn<DeliveryDTO, String> colPhone;

    private AdminController adminController;
    private ObservableList<DeliveryDTO> deliveryList;
    private LogisticsRepository logisticsRepository;
    private LogisticsServiceImpl logisticsServiceImpl;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("deliveryName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("deliveryEmail"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("deliveryPhone"));
        logisticsRepository = LogisticsRepository.getInstance();
        logisticsServiceImpl = new LogisticsServiceImpl();
        loadDeliveries();
    }

    public void loadDeliveries() {
        deliveryList = FXCollections.observableList(logisticsServiceImpl.getAllDeliveriesDTOs());
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
        DeliveryDTO selected = tablaDeliveries.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Advertencia", "Seleccione un Envío para eliminar", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Eliminar este Envío?", ButtonType.OK,
                ButtonType.CANCEL);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {

                String deliveryIdToDelete = selected.getDeliveryId();

                logisticsRepository.deleteDeliveryById(deliveryIdToDelete);

                loadDeliveries();
                showAlert("Éxito", "Envío eliminado", Alert.AlertType.INFORMATION);
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

package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.Address;
import co.edu.uniquindio.logisticsapp.model.Shipment;
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

public class UserShipmentListController {
    public Button btnBack;
    public Button btnDelete;

    @FXML
    private TableView<Shipment> tablaShipment;
    @FXML private TableColumn<Shipment, String> colId;
    @FXML private TableColumn<Shipment, String> colPackageType;
    @FXML private TableColumn<Shipment, Address> colOrigin;
    @FXML private TableColumn<Shipment, Address> colDestination;
    @FXML private TableColumn<Shipment, String> colDistance;
    @FXML private TableColumn<Shipment, String> colTotalCost;


    private AdminController adminController;
    private ObservableList<Shipment> shipmentsList;
    private LogisticsRepository logisticsRepository;
    private User currentUser;
    private LogisticsServiceImpl logisticsServiceImpl;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("shipmentId"));
        colPackageType.setCellValueFactory(new PropertyValueFactory<>("packageType"));
        colOrigin.setCellValueFactory(new PropertyValueFactory<>("origin"));
        colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        colDistance.setCellValueFactory(cellData ->
                        new SimpleStringProperty(String.format("%.2f km", cellData.getValue().getDistance())));
        colTotalCost.setCellValueFactory(cellData ->
                        new SimpleStringProperty(String.format("$ %,.2f", cellData.getValue().getTotalCost())+"COP"));
        logisticsRepository = LogisticsRepository.getInstance();
        logisticsServiceImpl = new LogisticsServiceImpl();

    }

    public void loadShipment(User currentUser) {
        this.currentUser = currentUser;
        shipmentsList = FXCollections.observableArrayList(
                logisticsServiceImpl.getRepository().getShipmentList()
                        .stream()
                        .filter(s -> s.getUser().equals(currentUser))
                        .toList()
        );
        tablaShipment.setItems(shipmentsList);
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
        Shipment selected = tablaShipment.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Advertencia", "Seleccione un envio para eliminar", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Eliminar este envio?", ButtonType.OK, ButtonType.CANCEL);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                logisticsRepository.deleteShipment(selected);
                loadShipment(selected.getUser());

                showAlert("Éxito", "Envio eliminado", Alert.AlertType.INFORMATION);
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

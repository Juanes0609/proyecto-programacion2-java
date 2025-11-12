package co.edu.uniquindio.logisticsapp.controller;

import java.util.List;

import co.edu.uniquindio.logisticsapp.model.Address;
import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.Shipment;
import co.edu.uniquindio.logisticsapp.model.User;
import co.edu.uniquindio.logisticsapp.report.CSVReportGenerator;
import co.edu.uniquindio.logisticsapp.report.IReportGenerator;
import co.edu.uniquindio.logisticsapp.report.PDFReportGenerator;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import co.edu.uniquindio.logisticsapp.service.LogisticsServiceImpl;
import co.edu.uniquindio.logisticsapp.util.observer.ShipmentObserver;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserShipmentListController implements ShipmentObserver {
    public Button btnBack;
    public Button btnDelete;
    private ObservableList<Shipment> userShipments;

    @FXML
    private TableView<Shipment> shipmentTable;
    @FXML
    private TableColumn<Shipment, String> colId;
    @FXML
    private TableColumn<Shipment, String> colPackageType;
    @FXML
    private TableColumn<Shipment, Address> colOrigin;
    @FXML
    private TableColumn<Shipment, Address> colDestination;
    @FXML
    private TableColumn<Shipment, String> colDistance;
    @FXML
    private TableColumn<Shipment, String> colTotalCost;
    @FXML
    private TableColumn<Shipment, String> colState;

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
        colState.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        colState.setCellFactory(column -> new TableCell<Shipment, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);

                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status);

                    switch (status.toLowerCase()) {
                        case "pendiente":
                            setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                            break;
                        case "en tránsito":
                            setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
                            break;
                        case "entregado":
                            setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                            break;
                        default:
                            setStyle("-fx-text-fill: black;");
                            break;
                    }
                }
            }
        });

        colDistance.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.format("%.2f km", cellData.getValue().getDistance())));

        colTotalCost.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.format("$ %,.2f", cellData.getValue().getTotalCost()) + "COP"));

        logisticsRepository = LogisticsRepository.getInstance();
        logisticsServiceImpl = new LogisticsServiceImpl();

    }

    public void loadShipment(User currentUser) {
        this.currentUser = currentUser;
        shipmentsList = FXCollections.observableArrayList(
                logisticsServiceImpl.getRepository().getShipmentList()
                        .stream()
                        .filter(s -> s.getUser().equals(currentUser))
                        .toList());
        shipmentTable.setItems(shipmentsList);
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
        Shipment selected = shipmentTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Advertencia", "Seleccione un envio para eliminar", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Eliminar este envio?", ButtonType.OK,
                ButtonType.CANCEL);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                logisticsRepository.deleteShipment(selected);
                loadShipment(selected.getUser());

                showAlert("Éxito", "Envio eliminado", Alert.AlertType.INFORMATION);
            }
        });
    }

    public void observeShipments(List<Shipment> shipments) {
        for (Shipment s : shipments) {
            s.addObserver(this);
        }
        userShipments.setAll(shipments);
    }

    @Override
    public void update(Shipment shipment) {
        Platform.runLater(() -> {
            int index = userShipments.indexOf(shipment);
            if (index >= 0) {
                userShipments.set(index, shipment);
                shipmentTable.refresh();
            }
        });
    }

    public void onGenerateReportClick(User currentUser, List<Shipment> shipmentList, String type) {

        IReportGenerator generator;

    if ("PDF".equalsIgnoreCase(type)) {
        generator = new PDFReportGenerator();
    } else if ("CSV".equalsIgnoreCase(type)) {
        generator = new CSVReportGenerator();
    } else {
        throw new IllegalArgumentException("Tipo de reporte no soportado.");
    }

    String fileName = "Reporte_" + currentUser.getFullName() + "." + type.toLowerCase();
    String filePath = System.getProperty("user.home") + "/Desktop/" + fileName;

        boolean success = generator.generateUserReport(shipmentsList, currentUser.getFullName(), filePath);

        if (success) {
            System.out.println("Reporte generado con éxito en: " + filePath);
        } else {
            System.err.println("Error al generar el reporte PDF.");
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alerta = new Alert(type);
        alerta.setTitle(title);
        alerta.setHeaderText(null);
        alerta.setContentText(message);
        alerta.showAndWait();
    }
}

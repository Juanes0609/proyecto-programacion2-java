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
import javafx.util.Callback;

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

    @FXML
    private TableColumn<Shipment, Void> colAccion;
    private UserController parentController;

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

        colDistance.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.format("%.2f km", cellData.getValue().getDistance())));

        colTotalCost.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.format("$ %,.2f", cellData.getValue().getTotalCost()) + "COP"));

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
                        case "no pagado":
                            setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                            break;
                        case "pagado":
                            setStyle("-fx-text-fill: #3498db; -fx-font-weight: bold;");
                            break;
                        case "en tránsito":
                            setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
                            break;
                        case "entregado":
                            setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                            break;
                        default:
                            setStyle("-fx-text-fill: #f39c12;");
                            break;
                    }
                }
            }
        });

        Callback<TableColumn<Shipment, Void>, TableCell<Shipment, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Shipment, Void> call(final TableColumn<Shipment, Void> param) {
                final TableCell<Shipment, Void> cell = new TableCell<Shipment, Void>() {

                    private final Button btn = new Button("Pagar");

                    {
                        btn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
                        btn.setOnAction((ActionEvent event) -> {
                            Shipment shipment = getTableView().getItems().get(getIndex());

                            if (parentController != null) {
                                System.out.println("Solicitando pago para: " + shipment.getShipmentId());
                                parentController.loadPaymentView(shipment);
                            } else {
                                System.err.println("Error: parentController es nulo en UserShipmentListController.");
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Shipment shipment = getTableView().getItems().get(getIndex());

                            if (shipment.getStatus().equalsIgnoreCase("No pagado")) {
                                setGraphic(btn);
                            } else {
                                setGraphic(null);
                            }
                        }
                    }
                };
                return cell;
            }
        };

        if (colAccion != null) {
            colAccion.setCellFactory(cellFactory);
        }

        logisticsRepository = LogisticsRepository.getInstance();
        logisticsServiceImpl = new LogisticsServiceImpl();
    }

    public void setParentController(UserController parentController) {
        this.parentController = parentController;
    }

    public void loadShipment(User currentUser) {
        this.currentUser = currentUser;

        List<Shipment> filteredShipments = logisticsServiceImpl.getRepository().getShipmentList()
                .stream()
                .filter(s -> s.getUser().equals(currentUser))
                .toList();

        if (userShipments == null) {
            userShipments = FXCollections.observableArrayList();

            shipmentTable.setItems(userShipments);
        }

        for (Shipment s : userShipments) {
            s.removeObserver(this);
        }

        userShipments.clear();
        userShipments.addAll(filteredShipments);

        for (Shipment s : filteredShipments) {
            s.addObserver(this);
        }
    }

    @FXML
    private void onBackToDashboard() {
        if (parentController != null) {
            parentController.backToUserDashboard();
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

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alerta = new Alert(type);
        alerta.setTitle(title);
        alerta.setHeaderText(null);
        alerta.setContentText(message);
        alerta.showAndWait();
    }
}

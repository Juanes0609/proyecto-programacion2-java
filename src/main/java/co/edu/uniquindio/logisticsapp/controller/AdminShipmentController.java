package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.Address;
import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.Shipment;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import co.edu.uniquindio.logisticsapp.util.state.InTransitState;
import co.edu.uniquindio.logisticsapp.util.state.ShipmentState;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.UUID;

public class AdminShipmentController {

    @FXML
    private Button btnBack;
    @FXML private TableView<Shipment> shipmentTable;
    @FXML private TableColumn<Shipment, Address> colDestination;
    @FXML private TableColumn<Shipment,String> colDistance;
    @FXML private TableColumn<Shipment, String> colId;
    @FXML private TableColumn<Shipment,Address> colOrigin;
    @FXML private TableColumn<Shipment, String> colPackageType;
    @FXML private TableColumn<Shipment, String> colState;
    @FXML private TableColumn<Shipment,String> colTotalCost;
    @FXML private TableColumn<Shipment,String> colDelivery;

    private ObservableList<Shipment> shipments;
    private ObservableList<Delivery> deliveries;
    private LogisticsRepository repository = LogisticsRepository.getInstance();
    private AdminController dashboardController;

    @FXML
    public void initialize() {
        repository = LogisticsRepository.getInstance();

        colId.setCellValueFactory(new SimpleStringPropertyExtractor<>(Shipment::getShipmentId));
        colPackageType.setCellValueFactory(new SimpleStringPropertyExtractor<>(Shipment::getPackageType));
        colOrigin.setCellValueFactory(new PropertyValueFactory<>("origin"));
        colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        colDistance.setCellValueFactory(new SimpleStringPropertyExtractor<>(s -> String.format("%.2f km", s.getDistance())));
        colTotalCost.setCellValueFactory(new SimpleStringPropertyExtractor<>(s -> String.format("$%,.2f COP", s.getTotalCost())));
        colState.setCellValueFactory(new SimpleStringPropertyExtractor<>(Shipment::getStatus));
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
        deliveries = FXCollections.observableArrayList(repository.getDeliveriesList());
        colDelivery.setCellFactory(column -> new TableCell<>() {
            private final ComboBox<Delivery> comboBox = new ComboBox<>();

            @Override
            protected void updateItem(String ignored, boolean empty) {
                super.updateItem(ignored, empty);

                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                    setText(null);
                    return;
                }

                Shipment shipment = getTableRow().getItem();

                if (shipment.getDelivery() != null) {
                    // Si ya tiene repartidor asignado
                    setText(shipment.getDelivery().getFullName());
                    setGraphic(null);
                } else {
                    // Mostrar ComboBox para asignar
                    comboBox.setItems(deliveries);
                    comboBox.setPromptText("Asignar Repartidor");
                    comboBox.setCellFactory(cb -> new ListCell<>() {
                        @Override
                        protected void updateItem(Delivery d, boolean empty) {
                            super.updateItem(d, empty);
                            setText(empty || d == null ? null : d.getFullName());
                        }
                    });

                    comboBox.setOnAction(e -> {
                        Delivery selected = comboBox.getSelectionModel().getSelectedItem();
                        if (selected != null) {
                            shipment.setDelivery(selected);
                            shipment.setState(new InTransitState());
                            selected.getShipments().add(shipment);
                            repository.updateShipment(shipment);
                            repository.updateDelivery(selected);
                            getTableView().refresh();
                            showAlert("Repartidor Asignado",
                                    "El envío " + shipment.getShipmentId() +
                                            " fue asignado a " + selected.getFullName(),
                                    Alert.AlertType.INFORMATION);
                        }
                    });

                    setText(null);
                    setGraphic(comboBox);
                }
            }
        });

        loadShipments();
    }

    private void loadShipments() {
        shipments = FXCollections.observableArrayList(repository.getShipmentList());
        shipmentTable.setItems(shipments);
    }


    @FXML
    void onBackToDashboard(ActionEvent event) {
        if (dashboardController != null) {
            dashboardController.backToDashboard();
        }
    }
    public void setDashboardController(AdminController controller) {
        this.dashboardController= controller;
    }
    private static class SimpleStringPropertyExtractor<T> implements javafx.util.Callback<TableColumn.CellDataFeatures<T, String>, javafx.beans.value.ObservableValue<String>> {
        private final java.util.function.Function<T, String> extractor;
        public SimpleStringPropertyExtractor(java.util.function.Function<T, String> extractor) {
            this.extractor = extractor;
        }
        @Override
        public javafx.beans.value.ObservableValue<String> call(TableColumn.CellDataFeatures<T, String> param) {
            return new SimpleStringProperty(extractor.apply(param.getValue()));
        }
    }

    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}

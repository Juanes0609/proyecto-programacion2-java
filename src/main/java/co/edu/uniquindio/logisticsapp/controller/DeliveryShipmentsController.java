package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.Address;
import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.Shipment;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import co.edu.uniquindio.logisticsapp.util.state.DeliveredState;
import co.edu.uniquindio.logisticsapp.util.state.InTransitState;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class DeliveryShipmentsController {

    @FXML private TableView<Shipment> shipmentTable;
    @FXML private TableColumn<Shipment, String> colId;
    @FXML private TableColumn<Shipment, String> colPackageType;
    @FXML private TableColumn<Shipment, Address> colOrigin;
    @FXML private TableColumn<Shipment, Address> colDestination;
    @FXML private TableColumn<Shipment, String> colDistance;
    @FXML private TableColumn<Shipment, String> colTotalCost;
    @FXML private TableColumn<Shipment, String> colState;

    @FXML private Button btnInRoute;
    @FXML private Button btnDelivered;
    @FXML private Button btnBack;

    private ObservableList<Shipment> shipments;
    private LogisticsRepository repository;
    private DashboardDeliveryController dashboardController;
    private Delivery currentDelivery;

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

        loadShipments();
    }

    private void loadShipments() {
        shipments = FXCollections.observableArrayList(repository.getShipmentList());
        shipmentTable.setItems(shipments);
    }

    @FXML
    void onMarkInRoute(ActionEvent event) {
        Shipment selected = shipmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Advertencia", "Selecciona un envío primero", Alert.AlertType.WARNING);
            return;
        }

        if (selected.getDelivery() == null && currentDelivery != null) {
            selected.setDelivery(currentDelivery);
        }

        selected.setState(new InTransitState());
        selected.notifyObservers();
        shipmentTable.refresh();
    }

    @FXML
    void onMarkDelivered(ActionEvent event) {
        Shipment selected = shipmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Advertencia", "Selecciona un envío primero", Alert.AlertType.WARNING);
            return;
        }

        selected.setState(new DeliveredState());
        selected.notifyObservers();
        shipmentTable.refresh();
    }

    @FXML
    void onBackToDashboard() {
        if (dashboardController != null) {
            dashboardController.backToDashboard();
        }
    }

    public void setDashboardController(DashboardDeliveryController controller) {
        this.dashboardController = controller;
    }

    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * Clase auxiliar para simplificar el uso de SimpleStringProperty con lambdas.
     */
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
    public void setCurrentDealer(Delivery delivery) {
        this.currentDelivery = delivery;
    }

}

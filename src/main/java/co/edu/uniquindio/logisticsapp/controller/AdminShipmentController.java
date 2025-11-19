package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.Address;
import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.Shipment;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import co.edu.uniquindio.logisticsapp.util.observer.AssignmentNotifier;
import co.edu.uniquindio.logisticsapp.util.state.InTransitState;
import co.edu.uniquindio.logisticsapp.util.state.ShipmentState;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.UUID;

public class AdminShipmentController {

    @FXML
    private Button btnBack;
    @FXML
    private TableView<Shipment> shipmentTable;
    @FXML
    private TableColumn<Shipment, Address> colDestination;
    @FXML
    private TableColumn<Shipment, String> colDistance;
    @FXML
    private TableColumn<Shipment, String> colId;
    @FXML
    private TableColumn<Shipment, Address> colOrigin;
    @FXML
    private TableColumn<Shipment, String> colPackageType;
    @FXML
    private TableColumn<Shipment, String> colState;
    @FXML
    private TableColumn<Shipment, String> colTotalCost;
    @FXML
    private TableColumn<Shipment, String> colDelivery;

    private ObservableList<Shipment> shipments;
    private ObservableList<Delivery> deliveries;
    private LogisticsRepository repository = LogisticsRepository.getInstance();
    private AdminController dashboardController;

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new SimpleStringPropertyExtractor<>(Shipment::getShipmentId));
        colPackageType.setCellValueFactory(new SimpleStringPropertyExtractor<>(Shipment::getPackageType));
        colOrigin.setCellValueFactory(new PropertyValueFactory<>("origin"));
        colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        colDistance.setCellValueFactory(
                new SimpleStringPropertyExtractor<>(s -> String.format("%.2f km", s.getDistance())));
        colTotalCost.setCellValueFactory(
                new SimpleStringPropertyExtractor<>(s -> String.format("$%,.2f COP", s.getTotalCost())));
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

        colDelivery.setCellValueFactory(
                new SimpleStringPropertyExtractor<>(s -> s.getDelivery() != null ? s.getDelivery().getFullName() : ""));

        final Callback<ListView<Delivery>, ListCell<Delivery>> deliveryCellFactory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Delivery d, boolean empty) {
                super.updateItem(d, empty);
                setText(empty || d == null ? null : d.getFullName());
            }
        };

        colDelivery.setCellFactory(column -> new TableCell<Shipment, String>() {

            private final ComboBox<Delivery> comboBox = new ComboBox<>();

            {

                comboBox.setItems(deliveries);
                comboBox.setPromptText("Asignar Repartidor");

                comboBox.setCellFactory(deliveryCellFactory);

                comboBox.setButtonCell(deliveryCellFactory.call(null));

                comboBox.setOnAction(e -> {
                    Delivery selected = comboBox.getSelectionModel().getSelectedItem();

                    Shipment currentShipment = getTableRow().getItem();

                    if (selected != null && currentShipment != null && currentShipment.getDelivery() == null) {

                        currentShipment.setDelivery(selected);
                        currentShipment.setState(new InTransitState());

                        selected.getShipments().add(currentShipment);

                        repository.updateShipment(currentShipment);
                        repository.updateDelivery(selected);

                        AssignmentNotifier.getInstance().notifyAssignmentChange();

                        getTableView().refresh();

                        showAlert("Repartidor Asignado",
                                "El envío " + currentShipment.getShipmentId() +
                                        " fue asignado a " + selected.getFullName(),
                                Alert.AlertType.INFORMATION);
                    }
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) { 
                    setGraphic(null);
                    setText(null);
                    return;
                }

                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                    setText(null);
                    return;
                }

                if (!item.isEmpty()) {

                    setText(item);
                    setGraphic(null);
                } else {

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
        this.dashboardController = controller;
    }

    private static class SimpleStringPropertyExtractor<T> implements
            javafx.util.Callback<TableColumn.CellDataFeatures<T, String>, javafx.beans.value.ObservableValue<String>> {
        private final java.util.function.Function<T, String> extractor;

        public SimpleStringPropertyExtractor(java.util.function.Function<T, String> extractor) {
            this.extractor = extractor;
        }

        @Override
        public javafx.beans.value.ObservableValue<String> call(TableColumn.CellDataFeatures<T, String> param) {
            T item = param.getValue();
            if(item == null) {
                return new SimpleStringProperty("");
            }
            String result = extractor.apply(item);
            
            if(result == null) {
                return new SimpleStringProperty("");
            }
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

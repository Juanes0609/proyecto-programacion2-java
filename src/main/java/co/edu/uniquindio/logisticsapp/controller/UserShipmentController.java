package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.Address;
import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.Shipment;
import co.edu.uniquindio.logisticsapp.model.User;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import co.edu.uniquindio.logisticsapp.util.state.FragileCost;
import co.edu.uniquindio.logisticsapp.util.state.NormalCost;
import co.edu.uniquindio.logisticsapp.util.strategy.CostStrategy;
import co.edu.uniquindio.logisticsapp.util.strategy.HeavyCost;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserShipmentController {
    @FXML
    private ComboBox<String> cbPackageType;
    @FXML
    private ComboBox<Address> cbOrigin;
    @FXML
    private ComboBox<Address> cbDestination;
    @FXML
    private TextField txtDistance;
    @FXML
    private TextField txtCost;
    @FXML
    private Label lblResult;

    private User currentUser;
    private final LogisticsRepository repository = LogisticsRepository.getInstance();
    private UserController parentController;

    public void setParentController(UserController parentController) {
        this.parentController = parentController;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        cbOrigin.setItems(FXCollections.observableArrayList(user.getFrequentAddresses()));
        cbDestination.setItems(FXCollections.observableArrayList(user.getFrequentAddresses()));
    }

    @FXML
    public void initialize() {
        cbPackageType.setItems(FXCollections.observableArrayList("Pesado", "Frágil", "Normal"));

    }

    @FXML
    private void onCalculateClick(ActionEvent event) {
        Address origin = cbOrigin.getValue();
        Address destination = cbDestination.getValue();

        if (origin == null || destination == null) {
            lblResult.setText("⚠️ Selecciona origen y destino");
            return;
        }

        double distance = origin.distanceTo(destination);
        txtDistance.setText(String.format("%.2f", distance));

        double cost = calculateCost(distance);
        txtCost.setText(String.format("%.2f", cost));
        lblResult.setText("✅ Costo calculado correctamente");
    }

    @FXML
    private void onGenerateClick(ActionEvent event) {
        Address origin = cbOrigin.getValue();
        Address destination = cbDestination.getValue();
        String packageType = cbPackageType.getValue();

        if (origin == null || destination == null || packageType == null) {
            showAlert("Error", "Debe completar todos los campos para generar el envío.", Alert.AlertType.ERROR);
            return;
        }

        double distance = origin.distanceTo(destination);
        double cost = calculateCost(distance);

        Shipment shipment = new Shipment(packageType, origin, destination, distance, cost);
        shipment.setUser(currentUser);
        repository.addShipment(shipment);

        lblResult.setText("Envío creado con estado: " + shipment.getStatus());

        Delivery delivery = new Delivery.Builder()
                .origin(origin)
                .cost(cost)
                .destination(destination)
                .weight(shipment.getDistance())
                .user(currentUser)
                .email(currentUser.getEmail())
                .build();


        if (parentController != null) {
            parentController.loadPaymentView(delivery);
        } else {
            showAlert("Error", "No se pudo acceder al panel de control para el pago.", Alert.AlertType.ERROR);
        }
    }

    private CostStrategy getStrategyForPackage(String packageType) {
        return switch (packageType.toLowerCase()) {
            case "pesado" -> new HeavyCost();
            case "frágil", "fragil" -> new FragileCost();
            default -> new NormalCost();
        };
    }

    private double calculateCost(double distance) {
        String packageType = cbPackageType.getValue();
        CostStrategy strategy = getStrategyForPackage(packageType);

        return strategy.calculateCost(distance);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alerta = new Alert(type);
        alerta.setTitle(title);
        alerta.setHeaderText(null);
        alerta.setContentText(message);
        alerta.showAndWait();
    }

}

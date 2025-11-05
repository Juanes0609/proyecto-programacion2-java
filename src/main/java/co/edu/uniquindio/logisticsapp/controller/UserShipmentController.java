package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.Address;
import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.Shipment;
import co.edu.uniquindio.logisticsapp.model.User;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
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

        Delivery delivery = new Delivery.Builder()
                                        .origin(origin)
                                        .cost(cost)
                                        .destination(destination)
                                        .weight(shipment.getDistance())
                                        .user(currentUser)
                                        .email(currentUser.getEmail())
                                        .build();
                                                   
        
        lblResult.setText("Envío generado. Redirigiendo a pago...");
        
        if (parentController != null) {
            parentController.loadPaymentView(delivery);
        } else {
             showAlert("Error Crítico", "No se pudo acceder al panel de control para el pago.", Alert.AlertType.ERROR);
        }
    }

    private double calculateCost(double distance) {
        String packageType = cbPackageType.getValue();
        double baseRate = 2000.0; // 💰 2000 COP por kilómetro

        double multiplier = switch (packageType != null ? packageType.toLowerCase() : "") {
            case "pesado" -> 1.5;
            case "frágil", "fragil" -> 1.3;
            default -> 1.0;
        };

        return distance * baseRate * multiplier;
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alerta = new Alert(type);
        alerta.setTitle(title);
        alerta.setHeaderText(null);
        alerta.setContentText(message);
        alerta.showAndWait();
    }

}

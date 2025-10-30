package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.User;
import co.edu.uniquindio.logisticsapp.model.Courier;
import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

public class AdminController {
    
    @FXML private Label lblUserName;
    @FXML private StackPane contentArea;
    
    private User currentUser;
    private final LogisticsRepository repository = LogisticsRepository.getInstance();

    // Método para JavaFX
    public void setCurrentUser(User user) {
        this.currentUser = user;
        updateUI();
    }
    
    private void updateUI() {
        if (currentUser != null) {
            lblUserName.setText("Admin: " + currentUser.getFullName());
        }
    }
    
    @FXML
    private void onManageUsers() {
        // Usar tu UserController existente
        UserController userController = new UserController();
        List<User> users = userController.getAllUsers();
        showAlert("Gestión Usuarios", "Total usuarios: " + users.size());
    }
    
    @FXML
    private void onManageCouriers() {
        // Usar tus métodos existentes de AdminController
        List<Courier> couriers = getCouriers();
        showAlert("Gestión Repartidores", "Total repartidores: " + couriers.size());
    }
    
    @FXML
    private void onViewMetrics() {
        // Usar tu AdminMetricsController existente
        AdminMetricsController metricsController = new AdminMetricsController();
        double avgTime = metricsController.calculateAverageDeliveryTime();
        double revenue = metricsController.getTotalRevenue();
        
        showAlert("Métricas", 
            "Tiempo promedio entrega: " + avgTime + " hrs\n" +
            "Ingresos totales: $" + revenue);
    }
    
    @FXML
    private void onLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/logisticsapp/view/Login.fxml"));
            Parent loginView = loader.load();
            
            Stage stage = (Stage) lblUserName.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(loginView));
            stage.setTitle("Sistema de Logística - Login");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Mantener tus métodos existentes
    public void addCourier(String name, String document, String phone, String status, String coverageZone) {
        boolean exists = repository.getCouriers()
                .stream()
                .anyMatch(c -> c.getDocument().equalsIgnoreCase(document));
        if (exists) {
            System.out.println("⚠️ Ya existe un repartidor con ese documento.");
            return;
        }
        Courier courier = new Courier(name, document, phone, status, coverageZone);
        repository.getCouriers().add(courier);
        System.out.println("✅ Courier agregado: " + name);
    }

    public List<Courier> getCouriers() {
        return repository.getCouriers();
    }

    public void changeCourierStatus(Courier courier, String newStatus) {
        if (courier != null) {
            courier.setStatus(newStatus);
            System.out.println("El Repartidor " + courier.getName() + " ahora está " + newStatus);
        }
    }
    
    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void assignDeliveryToCourier(Delivery delivery, Courier courier) {
        if (delivery == null || courier == null) return;
        delivery.setCourier(courier);
        delivery.setStatus("ASIGNADO");
        System.out.println("📦 Envío " + delivery.getDeliveryId() + " asignado a " + courier.getName());
    }
}

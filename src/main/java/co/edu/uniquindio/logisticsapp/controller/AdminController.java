package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.User;
import co.edu.uniquindio.logisticsapp.model.Dealer;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AdminController {

    @FXML
    private Label lblUserName;
    @FXML
    private StackPane contentArea;

    private User currentUser;
    private final LogisticsRepository repository = LogisticsRepository.getInstance();

    @FXML
    private Button btnLogout;

    @FXML
    private BorderPane mainContainer;

    @FXML
    private VBox sideBar;

    public void onLogout(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login");
    }

    public void onGoToList(ActionEvent actionEvent) {
        loadView("/UsersList.fxml", "listado");
    }

    public void onAddUser(ActionEvent actionEvent) {
        loadView("/Register.fxml", "registro");
    }

    public void onGoToListDelivery(ActionEvent actionEvent) {
        loadView("/DeliveryList.fxml", "listadoDeliveries");
    }

    private void loadView(String fxmlPath, String typeView) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();

            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);

            switch (typeView) {
                case "listado" -> {
                    UsersListController controller = loader.getController();
                    controller.setAdminController(this);
                    controller.loadUsers();
                }
                case "registro" -> {
                    RegisterController controller = loader.getController();
                    controller.setadminController(this);
                }
                case "listadoDeliveries" -> {
                    DeliveryListController controller = loader.getController();
                    controller.setadminController(this);
                    controller.loadDeliveries();
                }
            }

        } catch (IOException e) {
            showAlert("Error", "No se pudo cargar la vista: " + typeView, Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void onGoToDashboard() {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(new Label("Bienvenido al sistema de gestión de Administrador"));
    }

    void backToDashboard() {
        onGoToDashboard();
    }

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
        showAlert("Gestión Usuarios", "Total usuarios: " + users.size(), AlertType.INFORMATION);
    }

    @FXML
    private void onManageDealers() {
        // Usar tus métodos existentes de AdminController
        List<Dealer> dealersList = getDealers();
        showAlert("Gestión Repartidores", "Total repartidores: " + dealersList.size(), AlertType.INFORMATION);
    }

    @FXML
    private void onViewMetrics() {
        // Usar tu AdminMetricsController existente
        AdminMetricsController metricsController = new AdminMetricsController();
        double avgTime = metricsController.calculateAverageDeliveryTime();
        double revenue = metricsController.getTotalRevenue();

        showAlert("Métricas",
                "Tiempo promedio entrega: " + avgTime + " hrs\n" +
                        "Ingresos totales: $" + revenue,
                AlertType.INFORMATION);
    }

    @FXML
    private void onLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/co/edu/uniquindio/logisticsapp/view/Login.fxml"));
            Parent loginView = loader.load();

            Stage stage = (Stage) lblUserName.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(loginView));
            stage.setTitle("Sistema de Logística - Login");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Mantener tus métodos existentes
    public void addDealer(String name, String document, String phone, String status, String coverageZone) {
        boolean exists = repository.getDealersList()
                .stream()
                .anyMatch(c -> c.getDocument().equalsIgnoreCase(document));
        if (exists) {
            System.out.println("⚠️ Ya existe un repartidor con ese documento.");
            return;
        }
        Dealer dealer = new Dealer(name, document, phone, status, coverageZone);
        repository.getDealersList().add(dealer);
        System.out.println("✅ Courier agregado: " + name);
    }

    public List<Dealer> getDealers() {
        return repository.getDealersList();
    }

    public void changeDealerstatus(Dealer dealer, String status) {
        dealer.setStatus(status);
        System.out.println("Courier " + dealer.getName() + " is now " + status);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alerta = new Alert(type);
        alerta.setTitle(title);
        alerta.setHeaderText(null);
        alerta.setContentText(message);
        alerta.showAndWait();
    }
}

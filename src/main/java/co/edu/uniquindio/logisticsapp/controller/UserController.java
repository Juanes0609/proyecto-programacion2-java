package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.Shipment;
import co.edu.uniquindio.logisticsapp.model.User;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import co.edu.uniquindio.logisticsapp.service.LogisticsServiceImpl;
import co.edu.uniquindio.logisticsapp.service.ReportService;
import co.edu.uniquindio.poo.Model.Banco;
import co.edu.uniquindio.poo.Model.Cliente;
import co.edu.uniquindio.poo.Model.CuentaBancaria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class UserController {

    @FXML
    private Button btnLogout;

    @FXML
    private StackPane contentArea;

    @FXML
    private BorderPane mainContainer;

    @FXML
    private VBox sideBar;

    private User user;
    @FXML
    private void onGeneratePDFReport() {
        generateReport("pdf");
    }

    @FXML
    private void onGenerateCSVReport() {
        generateReport("csv");
    }

    private ReportService reportService =  new ReportService();

    private String userEmail;
    private User currentUser;
    private String userName;
    private LogisticsRepository repository = LogisticsRepository.getInstance();
    private final LogisticsServiceImpl service = new LogisticsServiceImpl();

    public void onLogout(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login");
    }

    public void onProfile(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserProfile.fxml"));
            Parent view = loader.load();

            UserProfileController controller = loader.getController();
            controller.setUserEmail(userEmail);
            controller.setDashboardUserController(this);

            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUserEmail(String email) {
        this.userEmail = email;
        System.out.println("Usuario logueado: " + email);
    }

    @FXML
    private void onGoToDashboard() {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(new Label("Bienvenido al sistema de gestión de Usuario"));
    }

    public void backToDashboard() {
        onGoToDashboard();
    }

    public void registerUser(String fullName, String email, String phone) {
        User newUser = new User(fullName, email, phone);
        service.registerUser(newUser);
        System.out.println("Usuario registrado correctamente: " + fullName);
    }

    public List<User> getAllUsers() {
        return service.getRepository().getUserList();
    }

    public User findUserByEmail(String email) {
        return service.getRepository().getUserList()
                .stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public void onAddressesClick(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserAddresses.fxml"));
            Parent view = loader.load();

            UserAdressesController controller = loader.getController();
            controller.setUser(repository.getUserByEmail(userEmail));

            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @FXML 
    public void onShipment(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserShipment.fxml"));
            Parent view = loader.load();

            UserShipmentController controller = loader.getController();
            User user = repository.getUserByEmail(userEmail);
            this.currentUser = user;
            
            controller.setCurrentUser(user);
            controller.setParentController(this);

            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onShipmentList(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserShipmentList.fxml"));
            Parent view = loader.load();

            UserShipmentListController controller = loader.getController();

            if (currentUser == null) {
                currentUser = repository.getUserByEmail(userEmail);
            }

            controller.loadShipment(currentUser);

            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPaymentView(Delivery delivery) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PaymentView.fxml"));
            Parent view = loader.load();

            PaymentController paymentController = loader.getController();

            paymentController.initializePayment(delivery, this);

            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);

        } catch (IOException e) {
            showAlert("Error", "No se pudo cargar la vista de pago.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void backToUserDashboard() {
        onGoToDashboard();
    }

    public CuentaBancaria getLoggedInUserAccount() throws IllegalStateException {
        if (currentUser == null) {
            currentUser = repository.getUserByEmail(userEmail);
            if (currentUser == null) {
                throw new IllegalStateException("Usuario no autenticado o no encontrado.");
            }
        }

        String identificacionCliente = currentUser.getEmail();

        Cliente clienteBanco = Banco.getInstance().buscarCliente(identificacionCliente);

        if (clienteBanco == null) {
            throw new IllegalStateException(
                    "No se encontró un cliente bancario con la identificación: " + identificacionCliente);
        }

        CuentaBancaria userAccount = clienteBanco.buscarCuenta();

        if (userAccount == null) {
            throw new IllegalStateException("El cliente bancario no tiene una cuenta principal asociada.");
        }

        return userAccount;
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alerta = new Alert(type);
        alerta.setTitle(title);
        alerta.setHeaderText(null);
        alerta.setContentText(message);
        alerta.showAndWait();
    }

    private void generateReport(String format) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Reporte de Entregas");
            fileChooser.setInitialFileName("Reporte_Entregas." + format);
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(format.toUpperCase() + " files", "*." + format)
            );

            File file = fileChooser.showSaveDialog(null);
            if (file == null) return;

            boolean success = reportService.generateUserReport(
                    userEmail,
                    currentUser.getFullName(),
                    format,
                    file.getAbsolutePath()
            );

            Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle("Generar Reporte");
            alert.setHeaderText(null);
            alert.setContentText(success ?
                    "✅ Reporte generado exitosamente en:\n" + file.getAbsolutePath() :
                    "❌ Error al generar el reporte.");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
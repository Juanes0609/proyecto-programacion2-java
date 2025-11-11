package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.service.ReportService;
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

public class DashboardDeliveryController {
    @FXML
    private Button btnLogout;

    @FXML
    private StackPane contentArea;

    @FXML
    private BorderPane mainContainer;

    @FXML
    private void onGeneratePDFReport() {
        generateReport("pdf");
    }

    @FXML
    private void onGenerateCSVReport() {
        generateReport("csv");
    }

    @FXML
    private VBox sideBar;
    private String deliveryEmail;
    private String deliveryName;

    private final ReportService reportService = new ReportService();

    public void onLogout(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login");
    }

    public void onProfile(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DeliveryProfile.fxml"));
            Parent view = loader.load();

            DeliveryProfileController controller  = loader.getController();
            controller.setDeliveryEmail(deliveryEmail);
            controller.setDashboardDeliveryController(this);

            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void onGoToDashboard() {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(new Label("Bienvenido al sistema de gestión de Repartidor"));
    }
    void backToDashboard() {
        onGoToDashboard();
    }

    public void setDeliveryEmail(String email) {
        this.deliveryEmail = email;
        System.out.println("📦 Email del repartidor recibido en Dashboard: " + email);
    }

    public void onViewShipment(ActionEvent actionEvent) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShipmentDelivery.fxml"));
            Parent view = loader.load();

            DeliveryShipmentsController controller = loader.getController();
            controller.setDashboardController(this);

            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);

        }catch (IOException e){
            e.printStackTrace();
        }

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

            System.out.println("📄 Generando reporte para:");
            System.out.println("  Email: " + deliveryEmail);
            System.out.println("  Nombre: " + deliveryName);
            System.out.println("  Formato: " + format);
            System.out.println("  Ruta: " + file.getAbsolutePath());

            boolean success = reportService.generateUserReport(
                    deliveryEmail,
                    deliveryName,
                    format,
                    file.getAbsolutePath()
            );

            System.out.println("✅ Resultado del reporte: " + success);

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

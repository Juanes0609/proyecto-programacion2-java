package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.PaymentMethod;
import co.edu.uniquindio.logisticsapp.util.factory.PaymentFactory;
import co.edu.uniquindio.logisticsapp.util.decorator.PriorityPaymentDecorator;

import co.edu.uniquindio.poo.AppP1.GUI.TransferirController;
import co.edu.uniquindio.poo.Model.CuentaBancaria;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PaymentController {

    @FXML
    private Label lblAmount;
    @FXML
    private StackPane contentArea;

    private Delivery currentDelivery;
    private UserController parentController;
    private double amountToPay;

    public void initializePayment(Delivery delivery, UserController parentController) {
        this.currentDelivery = delivery;
        this.parentController = parentController;
        this.amountToPay = delivery.getCost();
        lblAmount.setText("Monto a pagar: $" + String.format("%,.2f", amountToPay));

        contentArea.getChildren().clear();
    }

    @FXML
    public void onCreditCardPayment(ActionEvent event) {
        PaymentMethod creditCardPayment = PaymentFactory.createPayment("credit");
        boolean success = creditCardPayment.processPayment(amountToPay);
        handlePaymentResult(success, "Tarjeta Crédito (Factory)");
    }

    @FXML
    public void onDebitCardPayment(ActionEvent event) {
        PaymentMethod debitCardPayment = PaymentFactory.createPayment("debit");
        boolean success = debitCardPayment.processPayment(amountToPay);
        handlePaymentResult(success, "Tarjeta Débito (Factory)");
    }

    @FXML
    public void onPriorityPaymentClick(ActionEvent event) {

        PaymentMethod basePayment = PaymentFactory.createPayment("credit");

        PaymentMethod priorityPayment = new PriorityPaymentDecorator(basePayment);

        boolean success = priorityPayment.processPayment(amountToPay);

        handlePaymentResult(success, "Prioridad (+Tarifa Decorator)");
    }

    @FXML
    public void onIntegratedBankPayment(ActionEvent event) {
        try {

            CuentaBancaria userAccount = parentController.getLoggedInUserAccount();

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/co/edu/uniquindio/poo/AppP1/GUI/transferencia.fxml"));
            Parent root = loader.load();

            TransferirController bankController = loader.getController();
            bankController.setCuentaOrigen(userAccount);

            Stage stage = new Stage();
            stage.setTitle("Transferencia Bancaria - GUI del Banco");
            stage.setScene(new Scene(root));
            stage.initOwner(lblAmount.getScene().getWindow());
            stage.showAndWait();

            System.out.println("Integración con la GUI del JAR finalizada. Se asume pago exitoso.");
            handlePaymentResult(true, "Sistema Bancario Completo (GUI)");

        } catch (IllegalStateException e) {
            System.err.println("Error: No se pudo obtener la cuenta bancaria del usuario.");
            showAlert("Error de Banco", "No se pudo acceder a la cuenta bancaria.", Alert.AlertType.ERROR);
        } catch (IOException e) {
            System.err.println("Error al cargar la vista de transferencia: " + e.getMessage());
            showAlert("Error de Carga", "No se pudo cargar la interfaz del JAR bancario. Revise la ruta del FXML.",
                    Alert.AlertType.ERROR);
        }
    }

    private void handlePaymentResult(boolean success, String method) {
        if (success) {
            System.out.println("✅ Pago por " + method + " completado. Actualizando estado de envío.");
            currentDelivery.setStatus("Requested");
            parentController.backToUserDashboard();
        } else {
            showAlert("Pago Fallido", "El pago por " + method + " no se pudo completar. Intente con otro método.",
                    Alert.AlertType.WARNING);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void onCancel(ActionEvent event) {
        System.out.println("Pago cancelado. Volviendo al Dashboard.");
        parentController.backToUserDashboard();
    }
}
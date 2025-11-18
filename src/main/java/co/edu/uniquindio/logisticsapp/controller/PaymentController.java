package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.PaymentMethod;
import co.edu.uniquindio.logisticsapp.model.Shipment;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import co.edu.uniquindio.logisticsapp.util.factory.PaymentFactory;
import co.edu.uniquindio.logisticsapp.util.state.PayState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

import co.edu.uniquindio.poo.AppP1.GUI.TransferirController;
import co.edu.uniquindio.poo.Model.Banco;
import co.edu.uniquindio.poo.Model.CuentaBancaria;

public class PaymentController {

    @FXML
    private Label lblAmount;
    @FXML
    private StackPane contentArea;

    private Shipment currentShipment;
    private Delivery currentDelivery;
    private UserController parentController;
    private double amountToPay;

    private Banco banco = Banco.getInstance();
    private LogisticsRepository repository = LogisticsRepository.getInstance();

    private String currentPaymentType;

    public void initializePayment(Shipment shipment, Delivery delivery, UserController parentController,
            double amountToPay) {
        this.currentShipment = shipment;
        this.currentDelivery = delivery;
        this.parentController = parentController;

        this.amountToPay = shipment.getTotalCost();
        lblAmount.setText("Monto a pagar: $" + String.format("%,.2f", amountToPay));

        contentArea.getChildren().clear();
    }

    @FXML
    public void onCreditCardPayment(ActionEvent event) {
        loadCardForm("credit");
    }

    @FXML
    public void onDebitCardPayment(ActionEvent event) {
        loadCardForm("debit");
    }

    @FXML
    public void onBankTransferPayment(ActionEvent event) {
        if (amountToPay <= 0) {
            showAlert("Error de monto", "El monto a pagar debe ser mayor a cero.", Alert.AlertType.ERROR);
        }

        launchIntegratedBankPayment();
    }

    /**
     * Contiene la lógica para abrir la ventana externa de la GUI bancaria
     * y maneja el resultado del pago al cerrarse.
     */
    public void launchIntegratedBankPayment() {
        try {

            CuentaBancaria userAccount = parentController.getLoggedInUserAccount();
            CuentaBancaria logisticAccount = repository.getLogisticsAccount();

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/co/edu/uniquindio/poo/AppP1/GUI/transferencia.fxml"));
            Parent root = loader.load();

            TransferirController bankController = loader.getController();
            bankController.setCuentaOrigen(userAccount);
            bankController.setCuentaDestino(logisticAccount);

            bankController.setOnTransferComplete(success -> {

                handlePaymentResult(success, "Transferencia Bancaria");
            });

            Stage stage = new Stage();
            stage.setTitle("Transferencia Bancaria - GUI del Banco");
            stage.setScene(new Scene(root));
            stage.initOwner(lblAmount.getScene().getWindow());
            stage.showAndWait();

        } catch (IllegalStateException e) {
            System.err.println("Error: No se pudo obtener la cuenta bancaria del usuario. " + e.getMessage());
            showAlert("Error de Banco", "No se pudo acceder a la cuenta bancaria.", Alert.AlertType.ERROR);
        } catch (IOException e) {
            System.err.println("❌ ERROR: No se pudo cargar vista de transferencia: " + e.getMessage());
            showAlert("Error de Carga", "No se pudo cargar la interfaz bancaria.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Carga el formulario de tarjeta de crédito/débito en el StackPane.
     * 
     * @param paymentType Tipo de pago ("credit" o "debit").
     */
    private void loadCardForm(String paymentType) {
        this.currentPaymentType = paymentType;
        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/CardForm.fxml"));

            Node cardForm = loader.load();

            CardFormController formController = loader.getController();

            formController.initializeForm(this, amountToPay, paymentType);

            contentArea.getChildren().setAll(cardForm);
        } catch (IOException e) {
            System.err.println("Error al cargar el FXML del formulario de tarjeta: " + e.getMessage());
            showAlert("Error de Carga", "No se pudo cargar la interfaz del formulario de tarjeta.",
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Método llamado por el CardFormController al hacer clic en 'Pagar Ahora'.
     * Ejecuta la lógica de Factory y la simulación de descuento.
     */
    public void processCardPaymentFromForm(String cardNumber, String cvv, String month, String year, String cardHolder,
            String type) {

        System.out.println("Procesando pago con tarjeta tipo: " + type + "...");

        String expirationDate = month + "/" + year;

        PaymentMethod payment = PaymentFactory.createPayment(
                type,
                cardNumber,
                cardHolder,
                expirationDate,
                cvv);

        boolean success = false;

        if (payment != null) {

            success = payment.processPayment(amountToPay);
        }

        String methodName = (type.equals("credit") ? "Tarjeta Crédito" : "Tarjeta Débito");

        handlePaymentResult(success, methodName + " (Formulario)");
    }

    /**
     * Maneja el resultado del pago (éxito o fracaso).
     */
    private void handlePaymentResult(boolean success, String method) {
        if (success) {
            try {
                System.out.println("✅ Pago por " + method + " completado a nivel bancario.");

                currentShipment.setState(new PayState());

                if (currentDelivery != null) {
                    currentDelivery.setStatus("Pagado");
                }

                repository.updateShipment(currentShipment);
                if (currentDelivery != null) {
                    repository.updateDelivery(currentDelivery);
                }
                repository.saveRepository();

                showAlert("Pago Exitoso",
                        "El pago ha sido procesado y su envío está marcado como Pagado.",
                        Alert.AlertType.INFORMATION);

                parentController.backToUserDashboard();

            } catch (Exception e) {
                System.err.println("❌ ERROR Crítico al procesar el resultado de pago: " + e.getMessage());
                e.printStackTrace();

                showAlert("Error Interno",
                        "El dinero se transfirió, pero hubo un fallo al actualizar el estado del envío. Contacte soporte.",
                        Alert.AlertType.ERROR);
            }

        } else {

            showAlert("Pago Fallido",
                    "El pago por " + method + " no se pudo completar. Intente con otro método o revise los datos.",
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
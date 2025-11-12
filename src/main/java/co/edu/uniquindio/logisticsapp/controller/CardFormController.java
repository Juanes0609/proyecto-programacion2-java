package co.edu.uniquindio.logisticsapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import java.util.regex.Pattern;

public class CardFormController {

    @FXML
    private TextField txtCardNumber;
    @FXML
    private TextField txtCardHolder;
    @FXML
    private TextField txtMonth;
    @FXML
    private TextField txtYear;
    @FXML
    private PasswordField pwdCVV;

    private PaymentController parentController;
    private double amount;
    private String paymentType;

    private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("^\\d{13,16}$");
    private static final Pattern CVV_PATTERN = Pattern.compile("^\\d{3,4}$");
    private static final Pattern MONTH_PATTERN = Pattern.compile("^(0?[1-9]|1[0-2])$");

    public void initializeForm(PaymentController parentController, double amount, String type) {
        txtCardNumber.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            String cleanedText = newText.replaceAll("[^\\d]", "");

            if (cleanedText.length() > 16) {

                cleanedText = cleanedText.substring(0, 16);
            }

            if (!newText.equals(cleanedText)) {

                change.setText(cleanedText);
                change.setRange(0, change.getControlText().length());

                change.setCaretPosition(cleanedText.length());
                change.setAnchor(cleanedText.length());
                return change;
            }

            return change;
        }));

        this.parentController = parentController;
        this.amount = amount;
        this.paymentType = type;

        txtCardNumber.setTextFormatter(new TextFormatter<>(change -> {

            String newText = change.getControlNewText().replaceAll("[^\\d]", "");
            if (newText.length() > 16) {
                newText = newText.substring(0, 16);
            }
            change.setText(newText.substring(change.getControlText().length() - change.getText().length()));
            change.setCaretPosition(newText.length());
            change.setAnchor(newText.length());
            return change;
        }));
    }

    private boolean validateFields() {
        String error = "";

        String cardNumber = txtCardNumber.getText().replaceAll("\\s+", "");
        if (!CARD_NUMBER_PATTERN.matcher(cardNumber).matches()) {
            error += "• El número de tarjeta debe tener entre 13 y 16 dígitos.\n";
        }

        if (txtCardHolder.getText().trim().isEmpty()) {
            error += "• El nombre del titular no puede estar vacío.\n";
        }

        String month = txtMonth.getText().trim();
        if (!MONTH_PATTERN.matcher(month).matches()) {
            error += "• El mes de vencimiento (MM) no es válido (1-12).\n";
        }

        String year = txtYear.getText().trim();
        if (year.length() != 2 || !year.matches("^\\d{2}$")) {
            error += "• El año de vencimiento debe tener dos dígitos (AA).\n";
        } else {

            try {
                int currentYear = java.time.Year.now().getValue() % 100;
                int inputYear = Integer.parseInt(year);

                if (inputYear < currentYear) {
                    error += "• La tarjeta está vencida (el año no es válido).\n";
                }

            } catch (NumberFormatException e) {
                error += "• Error al procesar el año de vencimiento.\n";
            }
        }

        if (!CVV_PATTERN.matcher(pwdCVV.getText()).matches()) {
            error += "• El CVV debe tener 3 o 4 dígitos.\n";
        }

        if (error.isEmpty()) {
            return true;
        } else {
            showAlert("Datos Incompletos o Inválidos", error, Alert.AlertType.ERROR);
            return false;
        }
    }

    @FXML
    public void onProcessPaymentClick() {
        if (validateFields()) {
            String cardNumber = txtCardNumber.getText().replaceAll("\\s+", "");
            String cvv = pwdCVV.getText();

            parentController.processCardPaymentFromForm(
                    cardNumber,
                    cvv,
                    txtMonth.getText(),
                    txtYear.getText(),
                    txtCardHolder.getText(),
                    paymentType);
        }
    }

    @FXML
    public void onClearClick() {
        txtCardNumber.clear();
        txtCardHolder.clear();
        txtMonth.clear();
        txtYear.clear();
        pwdCVV.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
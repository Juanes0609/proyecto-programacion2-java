package co.edu.uniquindio.logisticsapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import java.time.YearMonth;
import java.util.regex.Pattern;
import java.util.function.UnaryOperator;

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
        applyCardNumberFormatter();

        this.parentController = parentController;
        this.amount = amount;
        this.paymentType = type;
    }

    private void applyCardNumberFormatter() {

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            if (newText.matches("\\d*") && newText.length() <= 16) {
                return change;
            }

            return null;
        };

        txtCardNumber.setTextFormatter(new TextFormatter<>(filter));
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
        String year = txtYear.getText().trim();

        if (!MONTH_PATTERN.matcher(month).matches()) {
            error += "• El mes de vencimiento (MM) no es válido (1-12).\n";
        } else if (year.length() != 2 || !year.matches("^\\d{2}$")) {
            error += "• El año de vencimiento debe tener dos dígitos (AA).\n";
        } else {

            try {
                int inputMonth = Integer.parseInt(month);
                int inputYear = 2000 + Integer.parseInt(year);

                YearMonth currentYearMonth = YearMonth.now();
                YearMonth inputYearMonth = YearMonth.of(inputYear, inputMonth);

                if (inputYearMonth.isBefore(currentYearMonth)) {
                    error += "• La tarjeta está vencida.\n";
                }
            } catch (NumberFormatException e) {
                error += "• Error al procesar la fecha de vencimiento.\n";
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
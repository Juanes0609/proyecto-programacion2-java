package co.edu.uniquindio.logisticsapp.util.factory;

import co.edu.uniquindio.logisticsapp.model.CreditCardPayment;
import co.edu.uniquindio.logisticsapp.model.DebitCardPayment;
import co.edu.uniquindio.logisticsapp.model.PaymentMethod;
import co.edu.uniquindio.logisticsapp.util.adapter.BankAppSimulator;
import co.edu.uniquindio.logisticsapp.util.adapter.AccountAdapter;

public class PaymentFactory {
    public static PaymentMethod createPayment(String type) {
        return switch (type.toLowerCase()) {
            case "bank" -> new AccountAdapter(new BankAppSimulator());
            case "credit" -> new CreditCardPayment();
            case "debit" -> new DebitCardPayment();
            default -> throw new IllegalArgumentException("Unknown payment type: " + type);
        };
    }
}

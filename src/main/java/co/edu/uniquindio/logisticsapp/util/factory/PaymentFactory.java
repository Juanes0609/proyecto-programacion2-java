package co.edu.uniquindio.logisticsapp.util.factory;

import co.edu.uniquindio.logisticsapp.model.CreditCardPayment;
import co.edu.uniquindio.logisticsapp.model.DebitCardPayment;
import co.edu.uniquindio.logisticsapp.model.PaymentMethod;

public class PaymentFactory {
    public static PaymentMethod createPayment(String type) {
        return createPayment(type, null, null, null, null);
    }
    public static PaymentMethod createPayment(String type, String cardNumber, 
                                              String cardHolder, String expirationDate, 
                                              String cvv) {
                                                  
        switch (type.toLowerCase()) {
            case "credit":
                return new CreditCardPayment(cardNumber, cardHolder, expirationDate, cvv);
                
            case "debit":
                return new DebitCardPayment(cardNumber, cardHolder, expirationDate, cvv);

            case "transfer":
                return null; 
            
            default:
                throw new IllegalArgumentException("Tipo de pago no soportado: " + type);
        }
    }
}

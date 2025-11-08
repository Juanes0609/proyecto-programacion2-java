package co.edu.uniquindio.logisticsapp.model;

public interface PaymentMethod {
    boolean processPayment (double amount);
    String getType();
}

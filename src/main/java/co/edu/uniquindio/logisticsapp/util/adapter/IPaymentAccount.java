package co.edu.uniquindio.logisticsapp.util.adapter;

import co.edu.uniquindio.logisticsapp.model.PaymentMethod;

public interface IPaymentAccount extends PaymentMethod {
    boolean processPayment(double amount);

}

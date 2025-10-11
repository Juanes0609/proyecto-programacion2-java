package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.Payment;
import co.edu.uniquindio.logisticsapp.service.LogisticsServiceImpl;

public class PaymentController {
    private final LogisticsServiceImpl service = new LogisticsServiceImpl();

    public void processPayment(double amount, String methodType) {
        Payment payment = service.processPayment(amount, methodType);
        System.out.println("Payment status: " + payment.getStatus());
    }
}


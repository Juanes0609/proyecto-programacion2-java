package co.edu.uniquindio.logisticsapp.dto;

import java.util.UUID;

public class PaymentDTO {
    private UUID paymentId;
    private double amount;
    private String method;
    private String result;

    public PaymentDTO() {}

    public PaymentDTO(UUID paymentId, double amount, String method, String result) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.method = method;
        this.result = result;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return paymentId + " " + amount + " " + method + " " + result;
    }

    
}


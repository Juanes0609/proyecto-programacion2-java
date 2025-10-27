package co.edu.uniquindio.logisticsapp.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Payment {
    private UUID paymentId;
    private double amount;
    private LocalDateTime timestamp;
    private PaymentMethod method;
    private String status;

    public Payment() {}

    public Payment(double amount, PaymentMethod method, String status) {
        this.paymentId = UUID.randomUUID();
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.method = method;
        this.status = status;
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

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return paymentId + " " + amount + " " + timestamp + " " + method
                + " " + status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    
}

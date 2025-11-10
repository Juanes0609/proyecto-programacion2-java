package co.edu.uniquindio.logisticsapp.dto;

public class PaymentDTO {
    private String paymentId;
    private double amount;
    private String method;
    private String result;
    private String status;
    public PaymentDTO() {}

    public PaymentDTO(String paymentId, double amount, String method, String result, String status) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.method = method;
        this.result = result;
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
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

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return paymentId + " " + amount + " " + method + " " + result;
    }

    
}


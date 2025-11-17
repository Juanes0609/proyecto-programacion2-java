package co.edu.uniquindio.logisticsapp.model;

import co.edu.uniquindio.logisticsapp.repository.SimulatedCardRepository;

public class CreditCardPayment implements co.edu.uniquindio.logisticsapp.model.PaymentMethod {

    private String cardNumber;
    private String cardHolder;
    private String expirationDate;
    private String cvv;

    public CreditCardPayment() {
    }

    public CreditCardPayment(String cardNumber, String cardHolder, String expirationDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    @Override
    public boolean processPayment(double amount) {
            // 1. Obtener el repositorio simulado
            SimulatedCardRepository repository = SimulatedCardRepository.getInstance();
            
            // 2. Intentar realizar el débito usando el número de tarjeta capturado del formulario
            boolean success = repository.debit(this.cardNumber, amount);
    
            if (success) {
                System.out.println("✅ Pago con Tarjeta de Crédito por $" + amount + " procesado y descontado.");
            } else {
                System.out.println("❌ Pago con Tarjeta de Crédito fallido. Fondos insuficientes o tarjeta inválida.");
            }
            
            return success;
        
    }

    // Getters and setters
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Override
    public String getType() {
        return "Tarjeta de Crédito";
    }
}

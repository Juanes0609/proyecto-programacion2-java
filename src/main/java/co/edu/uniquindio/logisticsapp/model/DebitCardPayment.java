package co.edu.uniquindio.logisticsapp.model;
import  co.edu.uniquindio.logisticsapp.model.PaymentMethod;

public class DebitCardPayment implements PaymentMethod {

    private String cardNumber;
    private String bankName;
    private String accountHolder;

    public DebitCardPayment() {}

    public DebitCardPayment(String cardNumber, String bankName, String accountHolder) {
        this.cardNumber = cardNumber;
        this.bankName = bankName;
        this.accountHolder = accountHolder;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("Processing debit card payment of $" + amount + "...");
        // Simulated payment logic
        return true;
    }

    // Getters and setters
    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getAccountHolder() { return accountHolder; }
    public void setAccountHolder(String accountHolder) { this.accountHolder = accountHolder; }

}

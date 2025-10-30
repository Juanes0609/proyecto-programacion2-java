package co.edu.uniquindio.logisticsapp.util.adapter;

import co.edu.uniquindio.logisticsapp.model.PaymentMethod;

public class AccountAdapter implements IPaymentAccount {
    
    // The Adaptee: the bank simulator instance
    private final BankAppSimulator bankApp;
    
    // Hardcoded accounts for demonstration/testing purposes
    // In a real app, these would be passed or retrieved from a user session.
    private static final String USER_ACCOUNT = "ACC00000001"; // Example: Carlos Pérez's account
    private static final String COMPANY_ACCOUNT = "ACC00043444"; // Example: Another account for the company

    public AccountAdapter(BankAppSimulator bankApp) {
        this.bankApp = bankApp;
    }

    /**
     * Implements the 'pay' method required by IPaymentAccount
     * by calling the adapted 'executeTransaction' method on the BankAppSimulator.
     * @param amount The amount to pay.
     * @return true if the payment was successful, false otherwise.
     */
    @Override
    public void pay(double amount) {
        // We map the simple 'pay' call to the complex transaction method
        // of the BankAppSimulator (which internally uses the external Banco methods).
        System.out.println("Attempting payment of $" + amount + " via Bank Account Adapter...");
        return bankApp.executeTransaction(USER_ACCOUNT, COMPANY_ACCOUNT, amount);
    }
}
package co.edu.uniquindio.logisticsapp.util.adapter;

import co.edu.uniquindio.logisticsapp.model.PaymentMethod;

public class BankTransactionAdapter implements PaymentMethod {
    private final BankAppSimulator bankApp;

    public BankTransactionAdapter(BankAppSimulator bankApp) {
        this.bankApp = bankApp;
    }

    @Override
    public boolean pay(double amount) {
        return bankApp.executeTransaction("userAccount", "companyAccount", amount);
    }
    
}

package co.edu.uniquindio.logisticsapp.util.adapter;

import co.edu.uniquindio.logisticsapp.util.interfaces.IPaymentAccount;

public class AccountAdapter implements IPaymentAccount {

    private final BankAppSimulator bankApp;
    
 
    private static final String USER_ACCOUNT = "ACC00000001"; 
    private static final String COMPANY_ACCOUNT = "ACC00043444"; 

    public AccountAdapter(BankAppSimulator bankApp) {
        this.bankApp = bankApp;
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Attempting payment of $" + amount + " via Bank Account Adapter...");
        return bankApp.executeTransaction(USER_ACCOUNT, COMPANY_ACCOUNT, amount);
    }
}
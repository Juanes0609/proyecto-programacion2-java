package co.edu.uniquindio.logisticsapp.util.adapter;

import co.edu.uniquindio.poo.Model.Banco;
import co.edu.uniquindio.poo.Model.CuentaBancaria;
import co.edu.uniquindio.poo.Model.SaldoInsuficienteException;
import co.edu.uniquindio.poo.Model.Transaccion;
import co.edu.uniquindio.poo.Model.EstadoTransaccion;
import java.time.LocalDateTime;

public class BankAppSimulator {

    // Uses the Singleton instance of the external Banco class
    private final Banco externalBank = Banco.getInstance();

    public boolean executeTransaction(String originAccountNumber, String destinationAccountNumber, double amount) {
        if (amount <= 0) {
            System.err.println("Transaction failed: Amount must be positive.");
            return false;
        }

        CuentaBancaria originAccount = externalBank.buscarCuentaPorNumero(originAccountNumber);
        CuentaBancaria destinationAccount = externalBank.buscarCuentaPorNumero(destinationAccountNumber);

        if (originAccount == null) {
            System.err.println("Transaction failed: Origin account not found.");
            return false;
        }

        if (destinationAccount == null) {
            System.err.println("Transaction failed: Destination account not found.");
            return false;
        }

        try {
            // 1. Withdraw from origin
            originAccount.retirarDinero(amount);

            // 2. Deposit to destination
            destinationAccount.depositarDinero(amount);

            // 3. Register the transaction (using a transfer type for tracking)
            Transaccion transaccion = new Transaccion(
                    LocalDateTime.now(),
                    "Transferencia", // Assuming payment is an external transfer/deposit
                    amount,
                    null, // Code is generated internally
                    EstadoTransaccion.COMPLETADA,
                    originAccountNumber,
                    destinationAccountNumber);
            externalBank.registrarTransaccion(transaccion);

            System.out.println("Transaction successful: $" + amount + " transferred from " + originAccountNumber
                    + " to " + destinationAccountNumber);
            return true;

        } catch (SaldoInsuficienteException e) {
            System.err.println("Transaction failed: Insufficient balance in origin account: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Transaction failed due to illegal argument: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Transaction failed: An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
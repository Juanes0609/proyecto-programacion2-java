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
            System.err.println("Transacción fallida: Saldo insuficiente.");
            return false;
        }

        CuentaBancaria originAccount = externalBank.buscarCuentaPorNumero(originAccountNumber);
        CuentaBancaria destinationAccount = externalBank.buscarCuentaPorNumero(destinationAccountNumber);

        if (originAccount == null) {
            System.err.println("Transacción fallida: Cuenta de origen no encontrada.");
            return false;
        }

        if (destinationAccount == null) {
            System.err.println("Transacción fallida: Cuenta de destino no encontrada.");
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

            System.out.println("Transacción exitosa: $" + amount + " hecha desde " + originAccountNumber
                    + " para " + destinationAccountNumber);
            return true;

        } catch (SaldoInsuficienteException e) {
            System.err.println("Transacción fallida, saldo insuficiente en: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Transacción fallida debido a un campo incorrecto " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Transacción fallida, ocurrió un error inesperado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
package co.edu.uniquindio.logisticsapp.repository;

import java.util.HashMap;
import java.util.Map;

// Clase Singleton para simular la base de datos de tarjetas
public class SimulatedCardRepository {

    // Mapa para almacenar el saldo de las tarjetas. 
    // Key: Número de Tarjeta (String), Value: Saldo (Double)
    private final Map<String, Double> cardBalances = new HashMap<>();

    private static final SimulatedCardRepository instance = new SimulatedCardRepository();

    private SimulatedCardRepository() {
        // DATOS QUEMADOS DE PRUEBA INICIALES 
        
        // 1. Tarjeta de CRÉDITO de Prueba (Saldo alto y siempre exitosa)
        cardBalances.put("1111222233334444", 5000000.00); 
        
        // 2. Tarjeta de DÉBITO de Prueba (Saldo bajo para probar fallos)
        cardBalances.put("9999888877776666", 5000000.00); 
        
        // 3. Tarjeta de CRÉDITO Fallida (Siempre denegada, saldo 0)
        cardBalances.put("0000111122223333", 0.00); 
        
        System.out.println("Cargados saldos iniciales de tarjetas para prueba.");
    }

    public static SimulatedCardRepository getInstance() {
        return instance;
    }

    public Double getBalance(String cardNumber) {
        // Retorna el saldo o 0.0 si la tarjeta no existe
        return cardBalances.getOrDefault(cardNumber, 0.0); 
    }

    public boolean debit(String cardNumber, double amount) {
        if (!cardBalances.containsKey(cardNumber)) {
            return false; // Tarjeta no encontrada
        }
        
        double currentBalance = cardBalances.get(cardNumber);
        
        if (currentBalance >= amount) {
            double newBalance = currentBalance - amount;
            cardBalances.put(cardNumber, newBalance);
            System.out.printf("💸 Descuento exitoso. Saldo restante para %s: $%.2f%n", 
                                cardNumber.substring(0, 4) + "...", newBalance);
            return true;
        } else {
            System.out.printf("❌ Fondos insuficientes. Saldo actual: $%.2f. Intento de débito: $%.2f%n", 
                                currentBalance, amount);
            return false;
        }
        
    }
}

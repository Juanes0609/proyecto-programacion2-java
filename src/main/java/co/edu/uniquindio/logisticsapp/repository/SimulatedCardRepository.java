package co.edu.uniquindio.logisticsapp.repository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SimulatedCardRepository implements Serializable {

    private static final String DATA_DIR = "app_data/";
    private static final String CARD_DATA_FILE = DATA_DIR + "card_data.ser";
    private static final long serialVersionUID = 1L;

    private final Map<String, Double> cardBalances = new HashMap<>();

    private static SimulatedCardRepository instance;

    private SimulatedCardRepository() {

    }

    private void initializeDefaultData() {
        if (cardBalances.isEmpty()) {
            System.out.println("🔄 Inicializando datos de tarjetas para pruebas...");

            cardBalances.put("1111222233334444", 5000000.00);

            cardBalances.put("9999888877776666", 5000000.00);

            cardBalances.put("0000111122223333", 0.00);

            cardBalances.put("5555666677778888", 1000000.00);

            System.out.println("✅ Datos de tarjetas inicializados: " + cardBalances.size() + " tarjetas cargadas");
            saveRepository();
        }
    }

    public static SimulatedCardRepository getInstance() {
        if (instance == null) {
            instance = loadRepository();
            if (instance == null) {
                instance = new SimulatedCardRepository();
                instance.initializeDefaultData();
            } else {
                System.out.println("✅ Datos de tarjetas cargados exitosamente - " +
                        instance.cardBalances.size() + " tarjetas disponibles");
            }
        }
        return instance;
    }

    private void saveRepository() {
        try {

            File dataDir = new File(DATA_DIR);
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CARD_DATA_FILE))) {
                oos.writeObject(this);
                System.out.println("💾 Datos de tarjetas guardados: " + cardBalances.size() + " tarjetas");
            }
        } catch (IOException e) {
            System.err.println("❌ Error al guardar datos de tarjetas: " + e.getMessage());
        }
    }

    private static SimulatedCardRepository loadRepository() {
        File dataFile = new File(CARD_DATA_FILE);
        if (!dataFile.exists()) {
            System.out.println("🆕 No se encontraron datos previos de tarjetas. Se crearán nuevos.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CARD_DATA_FILE))) {
            SimulatedCardRepository loadedRepo = (SimulatedCardRepository) ois.readObject();
            System.out.println("📂 Datos de tarjetas cargados exitosamente");
            return loadedRepo;
        } catch (FileNotFoundException e) {
            System.out.println("🆕 No se encontraron datos previos de tarjetas. Se crearán nuevos.");
            return null;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Error al cargar datos de tarjetas: " + e.getMessage());
            System.out.println("🔄 Se crearán datos nuevos...");
            return null;
        }
    }

    public Double getBalance(String cardNumber) {

        Double balance = cardBalances.getOrDefault(cardNumber, 0.0);
        System.out.println("💰 Consulta de saldo - Tarjeta: " + maskCardNumber(cardNumber) +
                " - Saldo: $" + balance);
        return balance;
    }

    public boolean debit(String cardNumber, double amount) {
        if (!cardBalances.containsKey(cardNumber)) {
            System.out.println("❌ Tarjeta no encontrada: " + maskCardNumber(cardNumber));
            return false;
        }

        double currentBalance = cardBalances.get(cardNumber);

        if (currentBalance >= amount) {
            double newBalance = currentBalance - amount;
            cardBalances.put(cardNumber, newBalance);
            System.out.printf("✅ Débito exitoso - Tarjeta: %s - Monto: $%.2f - Saldo restante: $%.2f%n",
                    maskCardNumber(cardNumber), amount, newBalance);
            saveRepository();
            return true;
        } else {
            System.out.printf("❌ Fondos insuficientes - Tarjeta: %s - Saldo actual: $%.2f - Intento de débito: $%.2f%n",
                    maskCardNumber(cardNumber), currentBalance, amount);
            return false;
        }
    }

    public boolean recharge(String cardNumber, double amount) {
        if (!cardBalances.containsKey(cardNumber)) {
            System.out.println("❌ Tarjeta no encontrada para recarga: " + maskCardNumber(cardNumber));
            return false;
        }

        double currentBalance = cardBalances.get(cardNumber);
        double newBalance = currentBalance + amount;
        cardBalances.put(cardNumber, newBalance);

        System.out.printf("💰 Recarga exitosa - Tarjeta: %s - Monto: $%.2f - Nuevo saldo: $%.2f%n",
                maskCardNumber(cardNumber), amount, newBalance);
        saveRepository();
        return true;
    }

    public boolean addCard(String cardNumber, double initialBalance) {
        if (cardBalances.containsKey(cardNumber)) {
            System.out.println("⚠️ La tarjeta ya existe: " + maskCardNumber(cardNumber));
            return false;
        }

        cardBalances.put(cardNumber, initialBalance);
        System.out.printf("🆕 Tarjeta agregada - Número: %s - Saldo inicial: $%.2f%n",
                maskCardNumber(cardNumber), initialBalance);
        saveRepository();
        return true;
    }

    public void printAllCards() {
        System.out.println("\n📋 Resumen de tarjetas disponibles:");
        if (cardBalances.isEmpty()) {
            System.out.println("   No hay tarjetas registradas");
        } else {
            cardBalances.forEach((card, balance) -> {
                System.out.printf("   💳 %s - Saldo: $%.2f%n", maskCardNumber(card), balance);
            });
        }
        System.out.println("Total: " + cardBalances.size() + " tarjetas\n");
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 8) {
            return "****";
        }
        return cardNumber.substring(0, 4) + "****" + cardNumber.substring(cardNumber.length() - 4);
    }

    public void resetToDefaultData() {
        cardBalances.clear();
        initializeDefaultData();
        System.out.println("🔄 Datos de tarjetas reseteados a valores por defecto");
    }
}
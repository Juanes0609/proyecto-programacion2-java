package co.edu.uniquindio.logisticsapp.repository;

import co.edu.uniquindio.logisticsapp.dto.DeliveryDTO;
import co.edu.uniquindio.logisticsapp.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class LogisticsRepository implements Serializable {

    private static final String DATA_DIR = System.getProperty("user.home") + "/.logistics_app/";
    private static final String DATA_FILE = DATA_DIR + "logistics_data.ser";
    private static final long serialVersionUID = 2L;

    private static LogisticsRepository instance;
    private final List<User> usersList;
    private final List<Dealer> dealersList;
    private final List<Delivery> deliveriesList;
    private final List<Payment> paymentsList;
    private final List<Shipment> shipmentList;

    private LogisticsRepository() {
        usersList = new ArrayList<>();
        dealersList = new ArrayList<>();
        paymentsList = new ArrayList<>();
        deliveriesList = new ArrayList<>();
        shipmentList = new ArrayList<>();

    }

    private void initializeDefaultData() {
        if (usersList.isEmpty()) {
            User sofia = new User("Sofia", "sofiaadmin@gmail.com", "3124008786");
            User juan = new User("Juan", "juanadmin@gmail.com", "3113322890");
            User victor = new User("Victor", "victor@gmail.com", "3024406422");
            Address casa = new Address(null, "Casa", "calle 33#33-03", "Armenia", 4.537083333, -75.68900000);
            Address trabajo = new Address(null, "Trabajo", "km 3 montenegro", "Montenegro", 4.54130555555,
                    -75.77161111);
            Address universidad = new Address(null, "Universidad", "Carrera 15N", "Armenia", 4.553888888,
                    -75.659972222);

            usersList.add(sofia);
            usersList.add(juan);
            usersList.add(victor);
            victor.addAddress(casa);
            victor.addAddress(trabajo);
            victor.addAddress(universidad);

            saveRepository();
        }
    }

    public static LogisticsRepository getInstance() {
        if (instance == null) {
            instance = loadRepository();
            if (instance == null) {
                instance = new LogisticsRepository();
                instance.initializeDefaultData();
            }
        }
        return instance;
    }

    public void addUser(User user) {
        usersList.add(user);
        saveRepository();
    }

    public void addCourier(Dealer dealer) {
        dealersList.add(dealer);
        saveRepository();
    }

    public void addShipment(Shipment shipment) {
        shipmentList.add(shipment);
        saveRepository();
    }

    public void addPayment(Payment payment) {
        paymentsList.add(payment);
        saveRepository();
    }

    public void addDelivery(Delivery delivery) {
        deliveriesList.add(delivery);
        saveRepository();
    }

    public void deleteShipment(Shipment shipment) {
        shipmentList.remove(shipment);
        saveRepository();
    }

    public void deleteUser(User user) {
        usersList.remove(user);
        saveRepository();
    }

    public void deleteDelivery(Delivery delivery) {
        deliveriesList.remove(delivery);
        saveRepository();
    }

    public void deleteDeliveryById(String deliveryId) {
        Optional<Delivery> deliveryToRemove = this.deliveriesList.stream()
                .filter(d -> d.getDeliveryId().equals(deliveryId))
                .findFirst();

        if (deliveryToRemove.isPresent()) {
            this.deliveriesList.remove(deliveryToRemove.get());
            System.out.println("✅ Entrega con ID " + deliveryId + " eliminada.");
            saveRepository();
        } else {
            System.out.println("⚠️ No se encontró la Entrega con ID " + deliveryId + ".");
        }
    }

    public void updateUser(User currentUser) {
        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getEmail().equalsIgnoreCase(currentUser.getEmail())) {
                usersList.set(i, currentUser);
                saveRepository();
                return;
            }
        }
    }

    public void updateShipment(Shipment updatedShipment) {
        for (int i = 0; i < shipmentList.size(); i++) {
            Shipment existing = shipmentList.get(i);
            if (existing.getShipmentId().equals(updatedShipment.getShipmentId())) {
                shipmentList.set(i, updatedShipment);
                System.out.println("✅ Envío actualizado: " + updatedShipment.getShipmentId());
                return;
            }
        }
        System.out.println("⚠️ No se encontró el envío con ID: " + updatedShipment.getShipmentId());
    }

    public void updateDelivery(Delivery currentDelivery) {
        for (int i = 0; i < deliveriesList.size(); i++) {
            if (deliveriesList.get(i).getEmail().equalsIgnoreCase(currentDelivery.getEmail())) {
                deliveriesList.set(i, currentDelivery);
                saveRepository();
                return;
            }
        }
    }

    public List<Shipment> getShipmentList() {
        return shipmentList;
    }

    public List<User> getUserList() {
        return usersList;
    }

    public List<Dealer> getDealersList() {
        return dealersList;
    }

    public List<Delivery> getDeliveriesList() {
        return deliveriesList;
    }

    public List<Payment> getPaymentsList() {
        return paymentsList;
    }

    public boolean existsUser(String email) {
        return usersList.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    public User login(String email, String phone) {
        return usersList.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email) && u.getPhone().equals(phone))
                .findFirst()
                .orElse(null);
    }

    public User getUserByEmail(String email) {
        for (User user : usersList) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    public Delivery getDeliveryByEmail(String email) {
        return deliveriesList.stream()
                .filter(d -> d.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public List<Delivery> getDeliveriesByUserEmail(String email) {
        return deliveriesList.stream()
                .filter(d -> d.getUser() != null && d.getUser().getEmail().equalsIgnoreCase(email))
                .toList();
    }

    public List<Delivery> getDeliveriesByDeliveryEmail(String email) {
        return deliveriesList.stream()
                .filter(d -> d != null && d.getEmail().equalsIgnoreCase(email))
                .toList();
    }

    public List<Shipment> getShipmentsByDeliveryEmail(String email) {
        return shipmentList.stream()
                .filter(s -> s.getDelivery() != null && s.getDelivery().getEmail().equalsIgnoreCase(email))
                .collect(Collectors.toList());
    }

    public void saveRepository() {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(this);
            System.out.println("✅ Datos guardados exitosamente en: " + DATA_FILE);
        } catch (IOException e) {
            System.err.println("❌ Error al guardar los datos: " + e.getMessage());
        }
    }

    private static LogisticsRepository loadRepository() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            LogisticsRepository loadedRepo = (LogisticsRepository) ois.readObject();
            System.out.println("✅ Datos cargados exitosamente desde: " + DATA_FILE);

            return loadedRepo;
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ Archivo de datos no encontrado. Iniciando con repositorio vacío.");
            return null;

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Error al cargar los datos: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Shipment> getShipmentsByUserEmail(String email) {
        return shipmentList.stream()
                .filter(s -> s.getUser() != null && s.getUser().getEmail().equalsIgnoreCase(email))
                .toList();
    }
}
package co.edu.uniquindio.logisticsapp.repository;

import co.edu.uniquindio.logisticsapp.dto.DeliveryDTO;
import co.edu.uniquindio.logisticsapp.model.*;

import java.util.*;

public class LogisticsRepository {
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

        //Datos quemados para usar la App
        User sofia = new User("Sofia", "sofiaadmin@gmail.com", "3124008786");
        User juan = new User("Juan", "juanadmin@gmail.com", "3113322890");
        User victor = new User("Victor", "victor@gmail.com", "3024406422");
        Address casa = new Address(null, "Casa", "calle 33#33-03", "Armenia", 4.537083333, -75.68900000);
        Address trabajo = new Address(null, "Trabajo", "km 3 montenegro", "Montenegro", 4.54130555555, -75.77161111);
        Address universidad = new Address(null, "Universidad", "Carrera 15N", "Armenia", 4.553888888, -75.659972222);

        usersList.add(sofia);
        usersList.add(juan);
        usersList.add(victor);
        victor.addAddress(casa);
        victor.addAddress(trabajo);
        victor.addAddress(universidad);
    }

    //Uso de Singleton
    public static LogisticsRepository getInstance() {
        if (instance == null) {
            instance = new LogisticsRepository();
        }
        return instance;
    }

    public List<Shipment> getShipmentList() {return shipmentList;}

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

    public void addUser(User user) {
        usersList.add(user);
    }

    public void addCourier(Dealer dealer) {
        dealersList.add(dealer);
    }

    public void addShipment(Shipment shipment) {shipmentList.add(shipment);}

    public void addPayment(Payment payment) {
        paymentsList.add(payment);
    }

    public void addDelivery(Delivery delivery) {
        deliveriesList.add(delivery);
    }

    public boolean existsUser(String email) {
        return usersList.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    public void deleteShipment(Shipment shipment) {shipmentList.remove(shipment);}

    public void deleteUser(User user) {
        usersList.remove(user);
    }

    public void deleteDelivery(Delivery delivery) {
        deliveriesList.remove(delivery);
    }

    public User login(String email, String phone) {
        return usersList.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email) && u.getPhone().equals(phone))
                .findFirst()
                .orElse(null);
    }

    public void updateUser(User currentUser) {
        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getEmail().equalsIgnoreCase(currentUser.getEmail())) {
                usersList.set(i, currentUser);
                break;
            }
        }
    }
    public void updateDelivery(Delivery currentDelivery) {
        for (int i = 0; i < deliveriesList.size(); i++) {
            if (deliveriesList.get(i).getEmail().equalsIgnoreCase(currentDelivery.getEmail())) {
                deliveriesList.set(i, currentDelivery);
                break;
            }
        }
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

    public void deleteDeliveryById(String deliveryId) {
        Optional<Delivery> deliveryToRemove = this.deliveriesList.stream()
            .filter(d -> d.getDeliveryId().equals(deliveryId))
            .findFirst();
        
        if (deliveryToRemove.isPresent()) {
            this.deliveriesList.remove(deliveryToRemove.get());
            System.out.println("✅ Entrega con ID " + deliveryId + " eliminada.");
        } else {
            System.out.println("⚠️ No se encontró la Entrega con ID " + deliveryId + ".");
        }
    }
}

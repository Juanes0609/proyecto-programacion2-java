package co.edu.uniquindio.logisticsapp.repository;

import co.edu.uniquindio.logisticsapp.model.Dealer;
import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.Payment;
import co.edu.uniquindio.logisticsapp.model.User;

import java.util.*;

public class LogisticsRepository {
    private static LogisticsRepository instance;
    private final List<User> users;
    private final List<Dealer> dealers;
    private final List<Delivery> deliveries;
    private final List<Payment> payments;


    private LogisticsRepository() {
        users = new ArrayList<>();
        users.add(new User("Sofia", "SofiaAdmin@gmail.com", "3124008786"));
        users.add(new User("Juan", "Juanadmin@gmail.com", "3113322890"));
        users.add(new User("Victor", "victor@gmail.com", "3024406422"));
        dealers = new ArrayList<>();
        payments = new ArrayList<>();
        deliveries = new ArrayList<>();
    }

    public static LogisticsRepository getInstance() {
        if (instance == null) {
            instance = new LogisticsRepository();
        }
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Dealer> getCouriers() {
        return dealers;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addCourier(Dealer dealer) {
        dealers.add(dealer);
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    public void addDelivery(Delivery delivery) {
        deliveries.add(delivery);
    }

    public boolean existsUser(String email) {
        return users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    public void deleteUser(User user) {
        users.remove(user);
    }

    public void deleteDelivery(Delivery delivery) {
        deliveries.remove(delivery);
    }

    public User login(String email, String phone) {
        return users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email) && u.getPhone().equals(phone))
                .findFirst()
                .orElse(null);
    }

    public void updateUser(User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equalsIgnoreCase(updatedUser.getEmail())) {
                users.set(i, updatedUser);
                break;
            }
        }
    }

    public User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }
}

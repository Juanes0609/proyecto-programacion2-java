package co.edu.uniquindio.logisticsapp.repository;

import co.edu.uniquindio.logisticsapp.model.Dealer;
import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.Payment;
import co.edu.uniquindio.logisticsapp.model.User;

import java.util.*;

public class LogisticsRepository {
    private static LogisticsRepository instance;
    private final List<User> usersList;
    private final List<Dealer> dealersList;
    private final List<Delivery> deliveriesList;
    private final List<Payment> paymentsList;

    private LogisticsRepository() {
        usersList = new ArrayList<>();
        usersList.add(new User("Sofia", "SofiaAdmin@gmail.com", "3124008786"));
        usersList.add(new User("Juan", "Juanadmin@gmail.com", "3113322890"));
        usersList.add(new User("Victor", "victor@gmail.com", "3024406422"));
        dealersList = new ArrayList<>();
        paymentsList = new ArrayList<>();
        deliveriesList = new ArrayList<>();
    }

    public static LogisticsRepository getInstance() {
        if (instance == null) {
            instance = new LogisticsRepository();
        }
        return instance;
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

    public void addUser(User user) {
        usersList.add(user);
    }

    public void addCourier(Dealer dealer) {
        dealersList.add(dealer);
    }

    public void addPayment(Payment payment) {
        paymentsList.add(payment);
    }

    public void addDelivery(Delivery delivery) {
        deliveriesList.add(delivery);
    }

    public boolean existsUser(String email) {
        return usersList.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

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

    public User getUserByEmail(String email) {
        for (User user : usersList) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }
}

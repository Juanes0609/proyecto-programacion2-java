package co.edu.uniquindio.logisticsapp.repository;

import co.edu.uniquindio.logisticsapp.model.*;
import co.edu.uniquindio.logisticsapp.model.Courier;
import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.Payment;
import co.edu.uniquindio.logisticsapp.model.User;

import java.util.*;

public class LogisticsRepository {
    private static LogisticsRepository instance;
    private final List<User> users;
    private final List<Courier> couriers;
    private final List<Delivery> deliveries;
    private final List<Payment> payments;


    private LogisticsRepository (){
        users = new ArrayList<>();
        users.add(new User("Sofia","admin@gmail.com","3124008786"));
        users.add(new User("Juan","admin@gmail.com","3113322890"));
        users.add(new User("Victor","victor@gmail.com","3024406422"));
        couriers = new ArrayList<>();
        payments = new ArrayList<>();
        deliveries = new ArrayList<>();
    }

    public static LogisticsRepository getInstance () { 
        if (instance == null){
            instance = new LogisticsRepository();
        }
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Courier> getCouriers() {
        return couriers;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void addUser (User user){
        users.add(user);
    }
    public void addCourier (Courier courier){
        couriers.add(courier);
    }
    public void addPayment (Payment payment){
        payments.add(payment);
    }

    public void addDelivery (Delivery delivery) { 
        deliveries.add(delivery);
    }
}

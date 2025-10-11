package co.edu.uniquindio.logisticsapp.repository;

import co.edu.uniquindio.logisticsapp.model.*;
import java.util.*;

public class LogisticsRepository {
    private static LogisticsRepository instance;
    private final List<User> users;
    private final List<Courier> couriers;
    private final List<Delivery> deliveries;
    private final List<Payment> payments;


    private LogisticsRepository (){
        users = new ArrayList<>();
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

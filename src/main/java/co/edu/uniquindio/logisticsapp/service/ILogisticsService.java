package co.edu.uniquindio.logisticsapp.service;

import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.Payment;
import co.edu.uniquindio.logisticsapp.model.User;
import java.util.List;

public interface ILogisticsService {
    void registerUser(User user);
    Delivery createDelivery(Delivery delivery);
    Payment processPayment(double amount, String methodType);
    List<Delivery> getDeliveriesByUser(User user);
    List<Payment> getAllPayments();
    List<User> getAllUsers();
}

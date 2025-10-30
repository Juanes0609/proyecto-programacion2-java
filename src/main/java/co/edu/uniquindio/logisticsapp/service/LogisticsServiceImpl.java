package co.edu.uniquindio.logisticsapp.service;

import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.Payment;
import co.edu.uniquindio.logisticsapp.model.PaymentMethod;
import co.edu.uniquindio.logisticsapp.model.User;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import co.edu.uniquindio.logisticsapp.util.factory.PaymentFactory;

import java.util.List;

public class LogisticsServiceImpl implements ILogisticsService{

    private final LogisticsRepository repository = LogisticsRepository.getInstance();

    @Override
    public void registerUser(User user) { 
        repository.addUser(user);
    }

    @Override
    public Delivery createDelivery (Delivery delivery) { 
        repository.addDelivery(delivery);
        System.out.println("Delivery creado para: " + delivery.getUser().getFullName());
        return delivery;
    }

    @Override
    public Payment processPayment(Delivery delivery, double amount, String methodType ) { 
        PaymentMethod method = PaymentFactory.createPayment(methodType);
        boolean success = method.pay(amount);
        Payment payment = new Payment(amount, method, success ? "Aprobado" : "Rechazado"); 
        repository.addPayment(payment);
        return payment;
    }

    @Override
    public List<Delivery> getDeliveriesByUser(User user) {
        return repository.getDeliveries().stream()
                .filter(d -> d.getUser().equals(user)).toList();
    }

    @Override
    public List<Payment> getAllPayments() {
        return repository.getPayments();
    }

    @Override
    public List<User> getAllUsers() {
        return repository.getUsers();
    }

    public LogisticsRepository getRepository() {
        return repository;
    }
    
}

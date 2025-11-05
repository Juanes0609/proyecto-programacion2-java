package co.edu.uniquindio.logisticsapp.service;

import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.Payment;
import co.edu.uniquindio.logisticsapp.model.PaymentMethod;
import co.edu.uniquindio.logisticsapp.model.User;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import co.edu.uniquindio.logisticsapp.util.factory.PaymentFactory;
import co.edu.uniquindio.logisticsapp.util.interfaces.ILogisticsService;

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
        boolean success = method.processPayment(amount);
        Payment payment = new Payment(amount, method, success ? "Aprobado" : "Rechazado"); 
        repository.addPayment(payment);
        return payment;
    }

    @Override
    public List<Delivery> getDeliveriesByUser(User user) {
        return repository.getDeliveriesList().stream()
                .filter(d -> d.getUser().equals(user)).toList();
    }

    @Override
    public List<Payment> getAllPayments() {
        return repository.getPaymentsList();
    }

    @Override
    public List<User> getAllUsers() {
        return repository.getUserList();
    }
    @Override
    public List<Delivery> getAllDeliveries() {return repository.getDeliveriesList();}

    public LogisticsRepository getRepository() {
        return repository;
    }
    
}

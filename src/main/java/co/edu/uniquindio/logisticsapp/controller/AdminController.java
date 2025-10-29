package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import co.edu.uniquindio.logisticsapp.model.Dealer;

import java.util.List;

public class AdminController {
    private final LogisticsRepository repository = LogisticsRepository.getInstance();

    public void addCourier(String name, String document, String phone, String status ,String coverageZone) {
        Dealer dealer = new Dealer(name, document, phone, status, coverageZone);
        repository.getCouriers().add(dealer);
        System.out.println("Courier added: " + name);
    }

    public List<Dealer> getCouriers() {
        return repository.getCouriers();
    }

    public void changeCourierStatus(Dealer dealer, String status) {
        dealer.setStatus(status);
        System.out.println("Courier " + dealer.getName() + " is now " + status);
    }
}


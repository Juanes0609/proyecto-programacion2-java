package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.Courier;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import java.util.List;

public class AdminController {
    private final LogisticsRepository repository = LogisticsRepository.getInstance();

    public void addCourier(String name, String document, String phone, String status ,String coverageZone) {
        Courier courier = new Courier(name, document, phone, status, coverageZone);
        repository.getCouriers().add(courier);
        System.out.println("Courier added: " + name);
    }

    public List<Courier> getCouriers() {
        return repository.getCouriers();
    }

    public void changeCourierStatus(Courier courier, String status) {
        courier.setStatus(status);
        System.out.println("Courier " + courier.getName() + " is now " + status);
    }
}


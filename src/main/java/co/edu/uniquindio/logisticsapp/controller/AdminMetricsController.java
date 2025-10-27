package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.*;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.Payment;

import java.util.*;
import java.util.stream.Collectors;

public class AdminMetricsController {
    private final LogisticsRepository repository = LogisticsRepository.getInstance();

    public double calculateAverageDeliveryTime() {
        return Math.random() * 5 + 1; 
    }

    public Map<String, Long> getDeliveriesByStatus() {
        return repository.getDeliveries().stream()
                .collect(Collectors.groupingBy(Delivery::getStatus, Collectors.counting()));
    }

    public double getTotalRevenue() {
        return repository.getPayments().stream()
                .filter(p -> p.getStatus().equals("Aprobado"))
                .mapToDouble(Payment::getAmount)
                .sum();
    }
}


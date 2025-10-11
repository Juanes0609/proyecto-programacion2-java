package co.edu.uniquindio.logisticsapp.dto;

import java.util.UUID;

public class DeliveryDTO {
    private UUID deliveryId;
    private String origin;
    private String destination;
    private double weight;
    private double cost;
    private String status;

    public DeliveryDTO() {}

    public DeliveryDTO(double cost, UUID deliveryId, String destination, String origin, String status, double weight) {
        this.cost = cost;
        this.deliveryId = deliveryId;
        this.destination = destination;
        this.origin = origin;
        this.status = status;
        this.weight = weight;
    }

    public UUID getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(UUID deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return deliveryId + " " + origin + " " + destination
                + " " + weight + " " + cost + " " + status;
    }

    
}


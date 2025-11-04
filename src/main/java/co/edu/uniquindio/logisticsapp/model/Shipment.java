package co.edu.uniquindio.logisticsapp.model;

import java.util.UUID;

public class Shipment {
    private UUID shipmentId;
    private String packageType;
    private Address origin;
    private Address destination;
    private double distance;
    private double totalCost;
    private User user;

    public Shipment(String packageType, Address origin, Address destination, double distance, double totalCost) {
        this.shipmentId = UUID.randomUUID();
        this.packageType = packageType;
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
        this.totalCost = totalCost;
        this.user = user;
    }

    public UUID getShipmentId() {
        return shipmentId;
    }

    public String getPackageType() {
        return packageType;
    }

    public Address getOrigin() {
        return origin;
    }

    public Address getDestination() {
        return destination;
    }

    public double getDistance() {
        return distance;
    }

    public double getTotalCost() {
        return totalCost;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return String.format("Envío %s: %s → %s (%.2f km, %.2f COP)",
                shipmentId.toString().substring(0, 8),
                origin.getAlias(),
                destination.getAlias(),
                distance,
                totalCost);
    }
}

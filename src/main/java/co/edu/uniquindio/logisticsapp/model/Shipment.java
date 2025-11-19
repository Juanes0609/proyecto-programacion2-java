package co.edu.uniquindio.logisticsapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uniquindio.logisticsapp.util.observer.ShipmentObserver;
import co.edu.uniquindio.logisticsapp.util.observer.ShipmentSubject;
import co.edu.uniquindio.logisticsapp.util.state.NotPayState;
import co.edu.uniquindio.logisticsapp.util.state.ShipmentState;

public class Shipment implements ShipmentSubject, Serializable {
    private ShipmentState state;
    private String shipmentId;
    private String packageType;
    private Address origin;
    private Address destination;
    private double distance;
    private double totalCost;
    private User user;
    private Delivery delivery;

    private transient List<ShipmentObserver> observers = new ArrayList<>();

    public Shipment(String packageType, Address origin, Address destination, double distance, double totalCost) {
        this.shipmentId = generateShortUUID();
        this.packageType = packageType;
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
        this.totalCost = totalCost;
        this.state = new NotPayState();
    }

    public void setState(ShipmentState state) {
        this.state = state;
        notifyObservers();
    }

    public String getShipmentId() {
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

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void nextState() {
        state.next(this);
    }

    public void previousState() {
        state.prev(this);
    }

    public ShipmentState getState() {
        return state;
    }

    @Override
    public void addObserver(ShipmentObserver observer) {
        initObservers();
        observers.add(observer);
    }

    @Override
    public void removeObserver(ShipmentObserver observer) {
        initObservers();
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        initObservers();
        for (ShipmentObserver observer : observers) {
            observer.update(this);
        }
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

    private String generateShortUUID() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }

    public String getStatus() {
        return (state != null) ?
                state.getState() : "Indefinido";
    }

    private void initObservers() {
        if (observers == null)
            observers = new ArrayList<>();
    }
}

package co.edu.uniquindio.logisticsapp.util.observer;

public interface ShipmentSubject {
    void addObserver(ShipmentObserver observer);
    void removeObserver(ShipmentObserver observer);
    void notifyObservers();
}

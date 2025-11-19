package co.edu.uniquindio.logisticsapp.util.observer;

import co.edu.uniquindio.logisticsapp.model.Shipment;

public interface ShipmentObserver {
    void update(Shipment shipment);
}

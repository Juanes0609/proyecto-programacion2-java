package co.edu.uniquindio.logisticsapp.util.state;

import co.edu.uniquindio.logisticsapp.model.Shipment;

public interface ShipmentState {
    void next(Shipment shipment);
    void prev(Shipment shipment);
    String getStatus();

}

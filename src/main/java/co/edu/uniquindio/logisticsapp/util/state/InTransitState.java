package co.edu.uniquindio.logisticsapp.util.state;

import co.edu.uniquindio.logisticsapp.model.Shipment;

public class InTransitState implements ShipmentState{
    @Override
    public void next(Shipment shipment) {
        shipment.setState(new DeliveredState());
    }

    @Override
    public void prev(Shipment shipment) {
        shipment.setState(new PendingState());
    }

    @Override
    public String getStatus() {
        return "En tránsito";
    }

}

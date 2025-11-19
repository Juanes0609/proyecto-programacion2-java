package co.edu.uniquindio.logisticsapp.util.state;

import java.io.Serializable;

import co.edu.uniquindio.logisticsapp.model.Shipment;

public class InTransitState implements ShipmentState, Serializable {

    private static final long serialVersionUID = 1L;
    
    @Override
    public void next(Shipment shipment) {
        shipment.setState(new DeliveredState());
    }

    @Override
    public void prev(Shipment shipment) {
        shipment.setState(new PendingState());
    }

    @Override
    public String getState() {
        return "En tránsito";
    }

}

package co.edu.uniquindio.logisticsapp.util.state;

import java.io.Serializable;

import co.edu.uniquindio.logisticsapp.model.Shipment;

public class PayState implements ShipmentState, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public void next(Shipment shipment) {
        shipment.setState(new InTransitState()); 
    }

    @Override
    public void prev(Shipment shipment) {
        shipment.setState(new NotPayState());
    }
    
    @Override
    public String getState() {
        return "Pagado";
    }
}

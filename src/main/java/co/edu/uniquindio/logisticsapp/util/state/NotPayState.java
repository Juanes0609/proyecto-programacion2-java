package co.edu.uniquindio.logisticsapp.util.state;

import java.io.Serializable;

import co.edu.uniquindio.logisticsapp.model.Shipment;

public class NotPayState implements ShipmentState, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public void next(Shipment shipment) {
        shipment.setState(new PayState()); 
    }

    @Override
    public void prev(Shipment shipment) {
        // No hay estado anterior
    }

    @Override
    public String getState() {
        return "No pagado";
    }
}

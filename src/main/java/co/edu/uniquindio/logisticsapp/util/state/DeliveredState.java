package co.edu.uniquindio.logisticsapp.util.state;

import java.io.Serializable;

import co.edu.uniquindio.logisticsapp.model.Shipment;

public class DeliveredState implements ShipmentState, Serializable {

    private static final long serialVersionUID = 1L;
    
    @Override
    public void next(Shipment shipment) {
        System.out.println("El envío ya fue entregado.");
    }

    @Override
    public void prev(Shipment shipment) {
        shipment.setState(new InTransitState());
    }

    @Override
    public String getState() {
        return "Entregado";
    }

    
}

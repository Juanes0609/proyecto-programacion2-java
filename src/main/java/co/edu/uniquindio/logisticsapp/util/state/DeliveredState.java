package co.edu.uniquindio.logisticsapp.util.state;

import co.edu.uniquindio.logisticsapp.model.Shipment;

public class DeliveredState implements ShipmentState {
    @Override
    public void next(Shipment shipment) {
        System.out.println("El envío ya fue entregado.");
    }

    @Override
    public void prev(Shipment shipment) {
        shipment.setState(new InTransitState());
    }

    @Override
    public String getStatus() {
        return "Entregado";
    }

    
}

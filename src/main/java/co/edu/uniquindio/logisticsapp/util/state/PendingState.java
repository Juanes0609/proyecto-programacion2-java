package co.edu.uniquindio.logisticsapp.util.state;

import co.edu.uniquindio.logisticsapp.model.Shipment;

public class PendingState implements ShipmentState{
    @Override
    public void next(Shipment shipment) {
        shipment.setState(new InTransitState());
    }

    @Override
    public void prev(Shipment shipment) {
        System.out.println("El envío aún no ha iniciado.");
    }

    @Override
    public String getState() {
        return "Pendiente";
    }
}


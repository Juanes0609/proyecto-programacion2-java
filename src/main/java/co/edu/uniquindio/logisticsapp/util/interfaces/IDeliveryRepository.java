package co.edu.uniquindio.logisticsapp.util.interfaces;

import co.edu.uniquindio.logisticsapp.model.Delivery;
import java.util.List;
import java.util.UUID;

public interface IDeliveryRepository {
    void saveDelivery(Delivery delivery);
    List<Delivery> findAllDeliveries();
    Delivery findDeliveryById(UUID id);
}

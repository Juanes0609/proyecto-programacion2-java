package co.edu.uniquindio.logisticsapp.util.mapper;

import java.util.List;
import java.util.stream.Collectors;

import co.edu.uniquindio.logisticsapp.dto.DeliveryDTO;
import co.edu.uniquindio.logisticsapp.model.Delivery;

public class DeliveryMapper {
    /**
     * Convierte una entidad Delivery en un DeliveryDTO.
     * Este es el mapeo principal para mostrar datos en la interfaz de usuario.
     * 
     * @param delivery La entidad Delivery de negocio.
     * @return El DTO simplificado para la capa de presentación.
     */
    public static DeliveryDTO entityToDto(Delivery delivery) {
        if (delivery == null) {
            return null;
        }

        String originCity = (delivery.getOrigin() != null) ? delivery.getOrigin().getCity() : "N/A";
        String destinationCity = (delivery.getDestination() != null) ? delivery.getDestination().getCity() : "N/A";
        String dealerEmail = (delivery.getEmail() != null) ? delivery.getEmail()  : "N/A";
        String dealerName = (delivery.getFullName() != null) ? delivery.getFullName() : "Sin asignar";

        return new DeliveryDTO(
                delivery.getDeliveryId(),
                originCity,
                destinationCity,
                delivery.getWeight(),
                delivery.getCost(),
                delivery.getStatus(),
                dealerName,
                delivery.getPhone(),
                dealerEmail);
    }

    /**
     * Convierte una lista de entidades Delivery a una lista de DeliveryDTO.
     * 
     * @param deliveries La lista de entidades Delivery.
     * @return Una lista de DTOs.
     */
    public static List<DeliveryDTO> entityListToDtoList(List<Delivery> deliveries) {
        if (deliveries == null) {
            return List.of();
        }
        return deliveries.stream()
                .map(DeliveryMapper::entityToDto)
                .collect(Collectors.toList());
    }
}

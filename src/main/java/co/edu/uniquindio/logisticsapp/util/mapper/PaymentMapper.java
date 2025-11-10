package co.edu.uniquindio.logisticsapp.util.mapper;

import co.edu.uniquindio.logisticsapp.dto.PaymentDTO;
import co.edu.uniquindio.logisticsapp.model.Payment;
import co.edu.uniquindio.logisticsapp.model.PaymentMethod;
import co.edu.uniquindio.logisticsapp.util.factory.PaymentFactory;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentMapper {

    /**
     * Convierte una entidad Payment en un PaymentDTO.
     * @param payment La entidad Payment de negocio.
     * @return El DTO simplificado para la capa de presentación.
     */
    public static PaymentDTO entityToDto(Payment payment) {
        if (payment == null) {
            return null;
        }
    
        PaymentDTO dto = new PaymentDTO();
        
        // Mapeo directo de campos
        dto.setPaymentId(payment.getPaymentId().toString()); 
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        
        // Mapeo del objeto a String usando el nuevo método getType()
        if (payment.getMethod() != null) {
            // CORRECCIÓN APLICADA AQUÍ:
            dto.setMethod(payment.getMethod().getType()); 
        } else {
            dto.setMethod("Desconocido");
        }
    
        return dto;
    }

    /**
     * Convierte una lista de entidades Payment a una lista de PaymentDTO.
     * @param payments La lista de entidades Payment.
     * @return Una lista de DTOs.
     */
    public static List<PaymentDTO> entityListToDtoList(List<Payment> payments) {
        if (payments == null) {
            return List.of();
        }
        return payments.stream()
                .map(PaymentMapper::entityToDto)
                .collect(Collectors.toList());
    }

    /**
     * Convierte un PaymentDTO a una entidad Payment. 
     * @param dto El PaymentDTO.
     * @return La entidad Payment.
     */
    public static Payment dtoToEntity(PaymentDTO dto) {
        if (dto == null) {
            return null;
        }

        Payment payment = new Payment();

        payment.setAmount(dto.getAmount());
        payment.setStatus(dto.getStatus());

        PaymentMethod method = PaymentFactory.createPayment(dto.getMethod());
        payment.setMethod(method);

        return payment;
    }
}

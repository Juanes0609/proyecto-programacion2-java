package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.Payment;
import co.edu.uniquindio.logisticsapp.model.PaymentMethod;
import co.edu.uniquindio.logisticsapp.service.LogisticsServiceImpl;
import co.edu.uniquindio.logisticsapp.util.factory.PaymentFactory;

public class PaymentController {
    private final LogisticsServiceImpl service = new LogisticsServiceImpl();

   public boolean processPayment(Delivery delivery, double amount, String methodType) {
        
        // 1. Obtener el método de pago usando el Factory
        PaymentMethod paymentProcessor = PaymentFactory.createPayment(methodType);
        
        // 2. Ejecutar el pago
        // Si el método es "bank", se usará el AccountAdapter (el banco antiguo)
        boolean paymentSuccessful = paymentProcessor.processPayment(amount);

        // 3. Registrar el resultado en el sistema de logística (si es necesario)
        if (paymentSuccessful) {
            // Lógica para registrar el éxito, actualizar el estado de la Delivery, etc.
            System.out.println("✅ Pago de $" + amount + " para entrega " + delivery.getDeliveryId() + " exitoso usando método: " + methodType);
        } else {
            System.out.println("❌ Pago de $" + amount + " fallido para entrega " + delivery.getDeliveryId());
        }        
        return paymentSuccessful;
    }
}

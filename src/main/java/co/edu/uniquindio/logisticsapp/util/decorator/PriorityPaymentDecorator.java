package co.edu.uniquindio.logisticsapp.util.decorator;

import co.edu.uniquindio.logisticsapp.model.PaymentMethod;

public class  PriorityPaymentDecorator extends PaymentDecorator {
    public PriorityPaymentDecorator(PaymentMethod payment) {
        super(payment);
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Añadiendo tarifa de prioridad al pago...");
        return super.processPayment(amount + 10);
    }    
}

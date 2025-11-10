package co.edu.uniquindio.logisticsapp.util.decorator;

import co.edu.uniquindio.logisticsapp.model.PaymentMethod;

public class PriorityPaymentDecorator implements PaymentMethod {
    private final PaymentMethod decoratedPayment;
    private static final double PRIORITY_FEE = 10.0; 

    public PriorityPaymentDecorator(PaymentMethod decoratedPayment) {
        this.decoratedPayment = decoratedPayment;
    }

    @Override
    public boolean processPayment(double amountToPay) {
        double newAmount = amountToPay + PRIORITY_FEE;
        System.out.println("✨ [DECORATOR] Aplicando Tarifa de Prioridad de $" + PRIORITY_FEE + ". Nuevo monto a procesar: $" + String.format("%,.2f", newAmount));
        
        return decoratedPayment.processPayment(newAmount);
    }

    public String getType() {
        return "Prioridad + " + decoratedPayment.getType(); 
    }
}
package co.edu.uniquindio.logisticsapp.util.decorator;

public class  PriorityPaymentDecorator extends PaymentDecorator {
    public PriorityPaymentDecorator(co.edu.uniquindio.logisticsapp.model.PaymentMethod payment) {
        super(payment);
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("Añadiendo tarifa de prioridad al pago...");
        return super.pay(amount + 10);
    }    
}

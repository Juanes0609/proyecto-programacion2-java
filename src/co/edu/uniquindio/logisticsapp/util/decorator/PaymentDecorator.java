package co.edu.uniquindio.logisticsapp.util.decorator;

import co.edu.uniquindio.logisticsapp.model.PaymentMethod;

public abstract class PaymentDecorator implements PaymentMethod {
    protected PaymentMethod wrappedPayment;

    public PaymentDecorator(PaymentMethod wrappedPayment) {
        this.wrappedPayment = wrappedPayment;
    }

    @Override
    public boolean pay(double amount) {
        return wrappedPayment.pay(amount);
    }
}

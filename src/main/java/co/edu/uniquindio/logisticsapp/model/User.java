package co.edu.uniquindio.logisticsapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private String userId;
    private String fullName;
    private String email;
    private String phone;
    private List<Address> frequentAddresses;
    private List<PaymentMethod> paymentMethods;
    private List<Shipment> shipments;

    public User() {
    }

    public User(String fullName, String email, String phone) {
        this.userId = generateShortUUID();
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.frequentAddresses = new ArrayList<>();
        this.paymentMethods = new ArrayList<>();
        this.shipments = new ArrayList<>();
    }

    public List<Shipment> getShipments() {
        return shipments;
    }

    public void addShipment(Shipment shipment) {
        shipments.add(shipment);
    }

    public void addAddress(Address address) {
        frequentAddresses.add(address);
    }

    public void addPaymentMethod(PaymentMethod method) {
        paymentMethods.add(method);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Address> getFrequentAddresses() {
        return frequentAddresses;
    }

    public void setFrequentAddresses(List<Address> frequentAddresses) {
        this.frequentAddresses = frequentAddresses;
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    @Override
    public String toString() {
        return userId + " " + fullName + " " + email + " " + phone
                + " " + frequentAddresses + " " + paymentMethods;
    }

    private String generateShortUUID() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }
}

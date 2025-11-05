package co.edu.uniquindio.logisticsapp.model;

import java.util.UUID;

public class Delivery {
    private final String deliveryId;
    private final Address origin;
    private final Address destination;
    private final double weight;
    private final double cost;
    private String status;
    private final User user;
    private Dealer dealer;

    private String email;
    private String fullName;
    private String phone;

    private Delivery(Builder builder) {

        this.deliveryId = (builder.deliveryId != null) ? builder.deliveryId :generateShortUUID();
        this.origin = builder.origin;
        this.destination = builder.destination;
        this.weight = builder.weight;
        this.cost = builder.cost;

        this.status = (builder.status != null) ? builder.status : "Pending Payment";
        this.user = builder.user;
        this.dealer = builder.dealer;
        this.email = builder.email;
        this.fullName = builder.fullName;
        this.phone = builder.phone;
    }

    public static class Builder {

        private String deliveryId;
        private Address origin;
        private Address destination;
        private double weight = 0.0;
        private double cost;
        private String status;
        private User user;
        private Dealer dealer;
        private String email;
        private String fullName;
        private String phone;

        public Builder deliveryId(String deliveryId) {
            this.deliveryId = deliveryId;
            return this;
        }

        public Builder origin(Address origin) {
            this.origin = origin;
            return this;
        }

        public Builder destination(Address destination) {
            this.destination = destination;
            return this;
        }

        public Builder weight(double weight) {
            this.weight = weight;
            return this;
        }

        public Builder cost(double cost) {
            this.cost = cost;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder dealer(Dealer dealer) {
            this.dealer = dealer;
            return this;
        }

        public Delivery build() {
            return new Delivery(this);
        }
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public Address getOrigin() {
        return origin;
    }

    public Address getDestination() {
        return destination;
    }

    public double getWeight() {
        return weight;
    }

    public double getCost() {
        return cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        String dealerName = (dealer != null) ? dealer.getName() : "Unassigned";
        String userName = (user != null) ? user.getFullName() : "Unknown User";

        return "Delivery ID: " + deliveryId +
                ", Origin: " + origin.getCity() +
                ", Destination: " + destination.getCity() +
                ", Cost: $" + String.format("%,.2f", cost) +
                ", Status: " + status +
                ", User: " + userName +
                ", Dealer: " + dealerName;
    }

    private String generateShortUUID() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }
}
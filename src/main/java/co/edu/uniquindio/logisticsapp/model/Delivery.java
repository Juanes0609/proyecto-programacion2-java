package co.edu.uniquindio.logisticsapp.model;

import java.util.UUID;



public class Delivery {
    private UUID deliveryId;
    private Address origin;
    private Address destination;
    private double weight;
    private double cost;
    private String status; // Requested / Assigned / OnRoute / Delivered / Incident
    private User user;
    private Dealer dealer;

    private Delivery (Builder builder) { 
        this.deliveryId = UUID.randomUUID();
        this.origin = builder.origin;
        this.destination = builder.destination;
        this.weight = builder.weight;
        this.cost = builder.cost;
        this.status = builder.status;
        this.user = builder.user;
        this.dealer = builder.dealer;


    }

    public static class Builder {
        private UUID deliveryId;
        private Address origin;
        private Address destination;
        private double weight;
        private double cost;
        private String status; // Requested / Assigned / OnRoute / Delivered / Incident
        private User user;
        private Dealer dealer;

        public Builder origin (Address origin) { 
            this.origin = origin;
            return this;
        }

        public Builder destination (Address destination) { 
            this.destination = destination;
            return this;
        }

        public Builder weight (double weight) { 
            this.weight = weight;
            return this;
        }

        public Builder cost (double cost) { 
            this.cost = cost;
            return this;
        }

        public Builder status (String status) { 
            this.status = status;
            return this;
        }

        public Builder user (User user) { 
            this.user = user;
            return this;
        }

        public Builder courier (Dealer dealer) {
            this.dealer = dealer;
            return this;
        }

        public Delivery build() {
            return new Delivery(this);
        }
    }

    public UUID getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(UUID deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Address getOrigin() {
        return origin;
    }

    public void setOrigin(Address origin) {
        this.origin = origin;
    }

    public Address getDestination() {
        return destination;
    }

    public void setDestination(Address destination) {
        this.destination = destination;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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

    public void setUser(User user) {
        this.user = user;
    }

    public Dealer getCourier() {
        return dealer;
    }

    public void setCourier(Dealer dealer) {
        this.dealer = dealer;
    }

    @Override
    public String toString() {
        return deliveryId + " " + origin + " " + destination
                + " " + weight + " " + cost + " " + status + " " + user.getFullName() + " "
                + dealer.getName();
    }    

    
}

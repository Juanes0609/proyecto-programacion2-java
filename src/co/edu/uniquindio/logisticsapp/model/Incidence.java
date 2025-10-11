package co.edu.uniquindio.logisticsapp.model;

import java.util.UUID;

public class Incidence {
    private UUID incidenceId;
    private String description;
    private String status;
    private Delivery relatedDelivery;

    public Incidence (){}

    public Incidence(String description, Delivery delivery) {
        this.incidenceId = UUID.randomUUID();
        this.description = description;
        this.status = "Open";
        this.relatedDelivery = delivery;
    }

    public UUID getIncidenceId() {
        return incidenceId;
    }

    public void setIncidenceId(UUID incidenceId) {
        this.incidenceId = incidenceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Delivery getRelatedDelivery() {
        return relatedDelivery;
    }

    public void setRelatedDelivery(Delivery relatedDelivery) {
        this.relatedDelivery = relatedDelivery;
    }

    
    
}

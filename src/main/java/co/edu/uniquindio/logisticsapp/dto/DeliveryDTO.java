package co.edu.uniquindio.logisticsapp.dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class DeliveryDTO {
    private final StringProperty deliveryId = new SimpleStringProperty();
    private final StringProperty originCity = new SimpleStringProperty();
    private final StringProperty destinationCity = new SimpleStringProperty();
    private final DoubleProperty weight = new SimpleDoubleProperty();
    private final DoubleProperty cost = new SimpleDoubleProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty dealerName = new SimpleStringProperty();

    public DeliveryDTO() {
    }

    public DeliveryDTO(String deliveryId, String originCity, String destinationCity, double weight, double cost,
            String status, String dealerName) {
        this.deliveryId.set(deliveryId);
        this.originCity.set(originCity);
        this.destinationCity.set(destinationCity);
        this.weight.set(weight);
        this.cost.set(cost);
        this.status.set(status);
        this.dealerName.set(dealerName);
    }

    public StringProperty deliveryIdProperty() {
        return deliveryId;
    }

    public StringProperty originCityProperty() {
        return originCity;
    }

    public StringProperty destinationCityProperty() {
        return destinationCity;
    }

    public DoubleProperty weightProperty() {
        return weight;
    }

    public DoubleProperty costProperty() {
        return cost;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty dealerNameProperty() {
        return dealerName;
    }

    public String getDeliveryId() {
        return deliveryId.get();
    }

    public String getOriginCity() {
        return originCity.get();
    }

    public StringProperty getDestinationCity() {
        return destinationCity;
    }

    public DoubleProperty getWeight() {
        return weight;
    }

    public DoubleProperty getCost() {
        return cost;
    }

    public StringProperty getStatus() {
        return status;
    }

    public StringProperty getDealerName() {
        return dealerName;
    }
}
package co.edu.uniquindio.logisticsapp.dto;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DeliveryDTO {

    private final StringProperty deliveryId = new SimpleStringProperty();
    private final StringProperty originCity = new SimpleStringProperty();
    private final StringProperty destinationCity = new SimpleStringProperty();
    private final DoubleProperty weight = new SimpleDoubleProperty();
    private final DoubleProperty cost = new SimpleDoubleProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty deliveryName = new SimpleStringProperty();
    private final StringProperty deliveryPhone = new SimpleStringProperty();
    private final StringProperty deliveryEmail = new SimpleStringProperty();

    public DeliveryDTO() {
    }

    public DeliveryDTO(String deliveryId, String originCity, String destinationCity, double weight, double cost,
            String status, String deliveryName, String deliveryPhone, String deliveryEmail) {
        this.deliveryId.set(deliveryId);
        this.originCity.set(originCity);
        this.destinationCity.set(destinationCity);
        this.weight.set(weight);
        this.cost.set(cost);
        this.status.set(status);
        this.deliveryName.set(deliveryName);
        this.deliveryPhone.set(deliveryPhone);
        this.deliveryEmail.set(deliveryEmail);
    }

    public String getDeliveryId() {
        return deliveryId.get();
    }

    public void setDeliveryId(String value) {
        this.deliveryId.set(value);
    }

    public StringProperty deliveryIdProperty() {
        return deliveryId;
    }

    public String getOriginCity() {
        return originCity.get();
    }

    public void setOriginCity(String value) {
        this.originCity.set(value);
    }

    public StringProperty originCityProperty() {
        return originCity;
    }

    public String getDestinationCity() {
        return destinationCity.get();
    }

    public void setDestinationCity(String value) {
        this.destinationCity.set(value);
    }

    public StringProperty destinationCityProperty() {
        return destinationCity;
    }

    public double getWeight() {
        return weight.get();
    }

    public void setWeight(double value) {
        this.weight.set(value);
    }

    public DoubleProperty weightProperty() {
        return weight;
    }

    public double getCost() {
        return cost.get();
    }

    public void setCost(double value) {
        this.cost.set(value);
    }

    public DoubleProperty costProperty() {
        return cost;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String value) {
        this.status.set(value);
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getDeliveryName() {
        return deliveryName.get();
    }

    public void setDeliveryName(String value) {
        this.deliveryName.set(value);
    }

    public StringProperty deliveryNameProperty() {
        return deliveryName;
    }

    public String getDeliveryPhone() {
        return deliveryPhone.get();
    }

    public void setDeliveryPhone(String value) {
        this.deliveryPhone.set(value);
    }

    public StringProperty deliveryPhoneProperty() {
        return deliveryPhone;
    }

    public String getDeliveryEmail() {
        return deliveryEmail.get();
    }

    public void setDeliveryEmail(String value) {
        this.deliveryEmail.set(value);
    }

    public StringProperty deliveryEmailProperty() {
        return deliveryEmail;
    }

}

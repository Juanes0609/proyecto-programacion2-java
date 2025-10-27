package co.edu.uniquindio.logisticsapp.model;

import java.util.UUID;

public class Address {
    private UUID addressId;
    private String alias;
    private String street;
    private String city;
    private double latitude;
    private double longitude;

    public Address() {}

    public Address(UUID addressId, String alias, String street, String city, double latitude, double longitude) {
        this.addressId = UUID.randomUUID();
        this.alias = alias;
        this.street = street;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public UUID getAddressId() {
        return addressId;
    }

    public void setAddressId(UUID addressId) {
        this.addressId = addressId;
    }

}

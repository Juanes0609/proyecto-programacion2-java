package co.edu.uniquindio.logisticsapp.model;

import java.io.Serializable;
import java.util.UUID;

public class Address implements Serializable{
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

    public double distanceTo(Address other) {
        final int R = 6371;

        double latDistance = Math.toRadians(other.getLatitude() - this.latitude);
        double lonDistance = Math.toRadians(other.getLongitude() - this.longitude);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(other.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return distance;
    }
        @Override
    public String toString() {
        return alias + " (" + city + ")";
    }
}

package co.edu.uniquindio.logisticsapp.model;

import java.util.UUID;

public class Dealer {
    private UUID courierId;
    private String name;
    private String document;
    private String phone;
    private String status; // Active / Inactive / OnRoute
    private String coverageZone;

    public Dealer() {}

    public Dealer(String name, String document, String phone, String status, String coverageZone) {
        this.courierId = UUID.randomUUID();
        this.name = name;
        this.document = document;
        this.phone = phone;
        this.status = status;
        this.coverageZone = coverageZone;
    }

    public UUID getCourierId() {
        return courierId;
    }

    public void setCourierId(UUID courierId) {
        this.courierId = courierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCoverageZone() {
        return coverageZone;
    }

    public void setCoverageZone(String coverageZone) {
        this.coverageZone = coverageZone;
    }

}

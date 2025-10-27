package co.edu.uniquindio.logisticsapp.dto;

public class TariffDTO {
    private double baseRate;
    private double weightRate;
    private double distanceRate;
    private double priorityRate;
    private double totalCost;

    public TariffDTO() {}

    public TariffDTO(double baseRate, double weightRate, double distanceRate, double priorityRate, double totalCost) {
        this.baseRate = baseRate;
        this.weightRate = weightRate;
        this.distanceRate = distanceRate;
        this.priorityRate = priorityRate;
        this.totalCost = totalCost;
    }

    public double getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(double baseRate) {
        this.baseRate = baseRate;
    }

    public double getWeightRate() {
        return weightRate;
    }

    public void setWeightRate(double weightRate) {
        this.weightRate = weightRate;
    }

    public double getDistanceRate() {
        return distanceRate;
    }

    public void setDistanceRate(double distanceRate) {
        this.distanceRate = distanceRate;
    }

    public double getPriorityRate() {
        return priorityRate;
    }

    public void setPriorityRate(double priorityRate) {
        this.priorityRate = priorityRate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    
}

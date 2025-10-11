package co.edu.uniquindio.logisticsapp.model;

public class Tariff {
    private double baseRate;
    private double weightRate;
    private double distanceRate;
    private double priorityRate;

    public Tariff (){}

    public Tariff(double baseRate, double weightRate, double distanceRate, double priorityRate) {
        this.baseRate = baseRate;
        this.weightRate = weightRate;
        this.distanceRate = distanceRate;
        this.priorityRate = priorityRate;
    }

    public double calculate(double weight, double distance, boolean priority) {
        double total = baseRate + weight * weightRate + distance * distanceRate;
        if (priority) total += priorityRate;
        return total;
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

    
}


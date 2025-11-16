package co.edu.uniquindio.logisticsapp.util.strategy;

public class NormalCost implements CostStrategy {
    @Override
    public double calculateCost(double distance) {
        return distance * 2000;
    }

}

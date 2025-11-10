package co.edu.uniquindio.logisticsapp.util.state;

import co.edu.uniquindio.logisticsapp.util.strategy.CostStrategy;

public class NormalCost implements CostStrategy {
    @Override
    public double calculateCost(double distance) {
        return distance * 2000;
    }

}

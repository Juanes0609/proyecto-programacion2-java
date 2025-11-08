package co.edu.uniquindio.logisticsapp.util.state;

import co.edu.uniquindio.logisticsapp.util.strategy.CostStrategy;

public class FragileCost implements CostStrategy{
    @Override
    public double calculateCost (double distance) {
        return distance * 2000 * 1.3;
    }

}

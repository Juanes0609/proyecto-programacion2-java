package co.edu.uniquindio.logisticsapp.util.strategy;

public class FragileCost implements CostStrategy{
    @Override
    public double calculateCost (double distance) {
        return distance * 2000 * 1.3;
    }

}

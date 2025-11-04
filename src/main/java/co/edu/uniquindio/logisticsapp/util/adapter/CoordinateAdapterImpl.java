package co.edu.uniquindio.logisticsapp.util.adapter;

public class CoordinateAdapterImpl implements CoordinateAdapter {

    private final DMSConverter dmsConverter;

    public CoordinateAdapterImpl(DMSConverter dmsConverter) {
        this.dmsConverter = dmsConverter;
    }

    @Override
    public double convert(String coordinate) {
        try {
            // Si es numérico (decimal simple)
            return Double.parseDouble(coordinate.trim());
        } catch (NumberFormatException e) {
            // Si no es decimal, intenta convertir desde DMS
            return dmsConverter.convertDMSToDecimal(coordinate);
        }
    }
}

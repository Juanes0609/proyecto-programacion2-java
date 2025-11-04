package co.edu.uniquindio.logisticsapp.util.adapter;

public class DMSConverter {
    public double convertDMSToDecimal(String dms) {
        dms = dms.trim().toUpperCase();

        String[] parts = dms.split("[°'\"]");
        if (parts.length < 3) throw new IllegalArgumentException("Formato DMS inválido: " + dms);

        double degrees = Double.parseDouble(parts[0].trim());
        double minutes = Double.parseDouble(parts[1].trim());
        double seconds = Double.parseDouble(parts[2].replaceAll("[^0-9.]", "").trim());

        double decimal = degrees + (minutes / 60) + (seconds / 3600);

        if (dms.contains("S") || dms.contains("W")) {
            decimal *= -1;
        }

        return decimal;
    }
}

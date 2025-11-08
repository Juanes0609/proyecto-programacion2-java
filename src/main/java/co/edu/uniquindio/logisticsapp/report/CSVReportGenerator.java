package co.edu.uniquindio.logisticsapp.report;

import co.edu.uniquindio.logisticsapp.model.Delivery;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;

public class CSVReportGenerator implements IReportGenerator{

    private static final String SEPARATOR = ",";
    private static final String NEW_LINE = "\n";

    /**
     * Genera un reporte CSV con la lista de envíos de un usuario.
     * 
     * @param deliveries La lista de envíos a incluir en el reporte.
     * @param filePath   La ruta donde se guardará el archivo CSV (ej:
     *                   "reporte.csv").
     */
    @Override
    public boolean generateUserReport(List<Delivery> deliveries, String userName, String filePath) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {

            writeHeader(writer);

            for (Delivery delivery : deliveries) {
                writeDeliveryRow(writer, delivery);
            }

            return true;

        } catch (IOException e) {
            System.err.println("Error al escribir el archivo CSV: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void writeHeader(PrintWriter writer) {

        String header = "ID_Envio" + SEPARATOR +
                "Ciudad_Origen" + SEPARATOR +
                "Ciudad_Destino" + SEPARATOR +
                "Peso_KG" + SEPARATOR +
                "Costo_Total" + SEPARATOR +
                "Estado";

        writer.write(header + NEW_LINE);
    }

    private void writeDeliveryRow(PrintWriter writer, Delivery delivery) {

        StringBuilder sb = new StringBuilder();

        sb.append(delivery.getDeliveryId()).append(SEPARATOR);
        sb.append(delivery.getOrigin().getCity()).append(SEPARATOR);
        sb.append(delivery.getDestination().getCity()).append(SEPARATOR);
        sb.append(String.format("%.2f", delivery.getWeight())).append(SEPARATOR);
        sb.append(String.format("%,.2f", delivery.getCost())).append(SEPARATOR);
        sb.append(delivery.getStatus());

        writer.write(sb.toString() + NEW_LINE);
    }
}

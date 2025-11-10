package co.edu.uniquindio.logisticsapp.service;

import java.util.List;

import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.report.CSVReportGenerator;
import co.edu.uniquindio.logisticsapp.report.IReportGenerator;
import co.edu.uniquindio.logisticsapp.report.PDFReportGenerator;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;

public class ReportService {
    private final LogisticsRepository repository = LogisticsRepository.getInstance();

    public boolean generateUserReport(String userEmail, String userName, String format, String filePath) {
        List<Delivery> userDeliveries = repository.getDeliveriesByUserEmail(userEmail);

        if (userDeliveries.isEmpty()) {
            System.out.println("⚠️ No hay entregas para generar reporte de " + userEmail);
            return false;
        }

        IReportGenerator generator;

        switch (format.toLowerCase()) {
            case "csv" -> generator = new CSVReportGenerator();
            case "pdf" -> generator = new PDFReportGenerator();
            default -> {
                System.out.println("❌ Formato no soportado: " + format);
                return false;
            }
        }

        boolean success = generator.generateUserReport(userDeliveries, userName, filePath);

        if (success) {
            System.out.println("✅ Reporte " + format.toUpperCase() + " generado con éxito en: " + filePath);
        } else {
            System.err.println("❌ Error al generar el reporte " + format.toUpperCase() + ".");
        }
        return success;
    }
}

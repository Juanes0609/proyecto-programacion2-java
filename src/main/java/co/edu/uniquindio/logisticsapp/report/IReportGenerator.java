package co.edu.uniquindio.logisticsapp.report;

import java.util.List;

import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.Shipment;

public interface IReportGenerator {

    /**
     * Define el contrato para generar cualquier tipo de reporte.
     * 
     * @param shipmentList La lista de envíos a reportar.
     * @param userName   El nombre del usuario.
     * @param filePath   La ruta completa donde se guardará el archivo.
     * @return true si el reporte se generó con éxito, false en caso contrario.
     */
    boolean generateUserReport(List<Shipment> shipmentList, String userName, String filePath);

}
package co.edu.uniquindio.logisticsapp.report;

import java.io.FileOutputStream;

import co.edu.uniquindio.logisticsapp.model.Shipment;
import co.edu.uniquindio.logisticsapp.model.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import co.edu.uniquindio.logisticsapp.model.Delivery;
import java.util.List;

public class PDFReportGenerator implements IReportGenerator{
    private static final Font FONT_TITLE = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLUE);
    private static final Font FONT_HEADER = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font FONT_NORMAL = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

    /**
     * Genera un reporte PDF con la lista de envíos de un usuario.
     * @param shipmentList La lista de envíos a incluir en el reporte.
     * @param userName El nombre completo del usuario.
     * @param filePath La ruta donde se guardará el archivo PDF.
     */

    @Override
    public boolean generateUserReport(List<Shipment> shipmentList, String userName, String filePath) {
        Document document = new Document();

        try {

            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            document.addTitle("Reporte de Envíos del Usuario: " + userName);
            document.addSubject("Lista de todos los envíos registrados.");

            addTitlePage(document, userName);
            addContent(document, shipmentList);

            document.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void addTitlePage(Document document, String userName) throws DocumentException {

        Paragraph title = new Paragraph("Reporte de Envíos", FONT_TITLE);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(Chunk.NEWLINE);

        Paragraph userHeader = new Paragraph("Usuario: " + userName, FONT_HEADER);
        userHeader.setAlignment(Element.ALIGN_LEFT);
        document.add(userHeader);

        document.add(new Paragraph("Fecha de Generación: " + new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()), FONT_NORMAL));
        document.add(Chunk.NEWLINE);
    }

    private void addContent(Document document, List<Shipment> shipmentList) throws DocumentException {

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        float[] columnWidths = { 1.5f, 2f, 2f, 1.5f, 1f };
        table.setWidths(columnWidths);

        addTableHeader(table);

        for (Shipment shipment : shipmentList) {
            addDeliveryRow(table, shipment);
        }

        document.add(table);
    }

    private void addTableHeader(PdfPTable table) {
        String[] headers = { "ID", "Origen", "Destino", "Costo ($)", "Estado" };
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, FONT_HEADER));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
    }

    private void addDeliveryRow(PdfPTable table, Shipment shipment) {
        String id = shipment.getShipmentId() != null ? shipment.getShipmentId() : "N/A";

        String origin = "N/A";
        if (shipment.getOrigin() != null) {
            String city = shipment.getOrigin().getCity() != null ? shipment.getOrigin().getCity() : "";
            String description = shipment.getOrigin().getAlias() != null ? shipment.getOrigin().getAlias(): "";
            origin = city + (description.isEmpty() ? "" : " (" + description + ")");
        }

        String destination = "N/A";
        if (shipment.getDestination() != null) {
            String city = shipment.getDestination().getCity() != null ? shipment.getDestination().getCity() : "";
            String description = shipment.getDestination().getAlias()!= null ? shipment.getDestination().getAlias() : "";
            destination = city + (description.isEmpty() ? "" : " (" + description + ")");
        }

        String cost = String.format("$%,.2f", shipment.getTotalCost());
        String status = shipment.getStatus() != null ? shipment.getStatus() : "Desconocido";

        table.addCell(new Phrase(id, FONT_NORMAL));
        table.addCell(new Phrase(origin, FONT_NORMAL));
        table.addCell(new Phrase(destination, FONT_NORMAL));
        table.addCell(new Phrase(cost, FONT_NORMAL));
        table.addCell(new Phrase(status, FONT_NORMAL));
    }


    public void generateAdminReport(String filePath, List<User> users, List<Delivery> deliveries, List<Shipment> shipments) {
        Document document = new Document(PageSize.A4);

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // 🔹 Título principal
            Paragraph title = new Paragraph("REPORTE GENERAL DEL SISTEMA", FONT_TITLE);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(Chunk.NEWLINE);

            // 🔹 Información general
            String fecha = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date());
            Paragraph info = new Paragraph("Fecha de generación: " + fecha, FONT_NORMAL);
            info.setAlignment(Element.ALIGN_LEFT);
            document.add(info);

            document.add(Chunk.NEWLINE);

            // 🔹 Totales del sistema
            Paragraph summaryTitle = new Paragraph("Resumen General", FONT_HEADER);
            summaryTitle.setAlignment(Element.ALIGN_LEFT);
            document.add(summaryTitle);

            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Usuarios registrados: " + users.size(), FONT_NORMAL));
            document.add(new Paragraph("Repartidores registrados: " + deliveries.size(), FONT_NORMAL));
            document.add(new Paragraph("Total de envíos: " + shipments.size(), FONT_NORMAL));

            document.add(Chunk.NEWLINE);

            // 🔹 Subtítulo tabla de envíos
            Paragraph tableTitle = new Paragraph("Listado de Envíos", FONT_HEADER);
            tableTitle.setAlignment(Element.ALIGN_LEFT);
            document.add(tableTitle);

            document.add(Chunk.NEWLINE);

            // 🔹 Tabla de envíos
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            float[] columnWidths = { 1.2f, 2f, 2f, 2f, 1.5f };
            table.setWidths(columnWidths);

            // Encabezados de tabla
            String[] headers = { "ID Envío", "Tipo Paquete", "Origen", "Destino", "Estado" };
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, FONT_HEADER));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                table.addCell(cell);
            }

            // Filas
                for (Shipment s : shipments) {
                    String origin = "N/A";
                    if (s.getOrigin() != null) {
                        String city = s.getOrigin().getCity() != null ? s.getOrigin().getCity() : "";
                        String description = s.getOrigin().getAlias() != null ? s.getOrigin().getAlias() : "";
                        origin = city + (description.isEmpty() ? "" : " (" + description + ")");
                    }

                    String destination = "N/A";
                    if (s.getDestination() != null) {
                        String city = s.getDestination().getCity() != null ? s.getDestination().getCity() : "";
                        String description = s.getDestination().getAlias() != null ? s.getDestination().getAlias() : "";
                        destination = city + (description.isEmpty() ? "" : " (" + description + ")");
                    }

                    table.addCell(new Phrase(s.getShipmentId() != null ? s.getShipmentId() : "N/A", FONT_NORMAL));
                    table.addCell(new Phrase(s.getPackageType() != null ? s.getPackageType() : "N/A", FONT_NORMAL));
                    table.addCell(new Phrase(origin, FONT_NORMAL));
                    table.addCell(new Phrase(destination, FONT_NORMAL));
                    table.addCell(new Phrase(s.getStatus() != null ? s.getStatus() : "Desconocido", FONT_NORMAL));
                }



                document.add(table);

            document.add(Chunk.NEWLINE);

            // 🔹 Mensaje final
            Paragraph footer = new Paragraph("Fin del reporte.", FONT_NORMAL);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}

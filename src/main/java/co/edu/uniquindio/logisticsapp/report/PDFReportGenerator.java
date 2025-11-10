package co.edu.uniquindio.logisticsapp.report;

import java.io.FileOutputStream;

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
     * @param deliveries La lista de envíos a incluir en el reporte.
     * @param userName El nombre completo del usuario.
     * @param filePath La ruta donde se guardará el archivo PDF.
     */

    @Override
    public boolean generateUserReport(List<Delivery> deliveries, String userName, String filePath) {
        Document document = new Document();

        try {

            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            document.addTitle("Reporte de Envíos del Usuario: " + userName);
            document.addSubject("Lista de todos los envíos registrados.");

            addTitlePage(document, userName);
            addContent(document, deliveries);

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

        document.add(new Paragraph("Fecha de Generación: " + java.time.LocalDate.now().toString(), FONT_NORMAL));
        document.add(Chunk.NEWLINE);
    }

    private void addContent(Document document, List<Delivery> deliveries) throws DocumentException {

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        float[] columnWidths = { 1.5f, 2f, 2f, 1.5f, 1f, 1f };
        table.setWidths(columnWidths);

        addTableHeader(table);

        for (Delivery delivery : deliveries) {
            addDeliveryRow(table, delivery);
        }

        document.add(table);
    }

    private void addTableHeader(PdfPTable table) {
        String[] headers = { "ID", "Origen", "Destino", "Peso (kg)", "Costo ($)", "Estado" };
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, FONT_HEADER));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
    }

    private void addDeliveryRow(PdfPTable table, Delivery delivery) {

        table.addCell(new Phrase(delivery.getDeliveryId(), FONT_NORMAL));

        table.addCell(new Phrase(delivery.getOrigin().getCity(), FONT_NORMAL));

        table.addCell(new Phrase(delivery.getDestination().getCity(), FONT_NORMAL));

        table.addCell(new Phrase(String.format("%.2f", delivery.getWeight()), FONT_NORMAL));

        table.addCell(new Phrase(String.format("%,.2f", delivery.getCost()), FONT_NORMAL));

        table.addCell(new Phrase(delivery.getStatus(), FONT_NORMAL));
    }
}

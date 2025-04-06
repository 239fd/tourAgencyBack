package by.bsuir.touragency.service.impl;

import by.bsuir.touragency.dto.AnalyticsDTO;
import by.bsuir.touragency.service.PDFExportService;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
public class PDFExportServiceImpl implements PDFExportService {

    @Override
    public byte[] exportAnalyticsReportToPdf(AnalyticsDTO analytics) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        Paragraph title = new Paragraph("Analytics Report")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(18);
        document.add(title);
        document.add(new Paragraph(" "));

        document.add(new Paragraph("Total Orders: " + analytics.getTotalOrders()));
        document.add(new Paragraph("Total Clients: " + analytics.getTotalClients()));
        document.add(new Paragraph("Overall Average Age: " + analytics.getOverallAverageAge()));
        document.add(new Paragraph("Average Age (Male): " + analytics.getAverageAgeMale()));
        document.add(new Paragraph("Average Age (Female): " + analytics.getAverageAgeFemale()));
        document.add(new Paragraph("Percentage Male: " + analytics.getPercentageMale() + "%"));
        document.add(new Paragraph("Percentage Female: " + analytics.getPercentageFemale() + "%"));
        document.add(new Paragraph(" "));

        float[] columnWidths = {200F, 100F};
        Table table = new Table(columnWidths);
        table.addHeaderCell("Order Status");
        table.addHeaderCell("Count");
        if (analytics.getOrdersStatusCount() != null) {
            for (Map.Entry<String, Long> entry : analytics.getOrdersStatusCount().entrySet()) {
                table.addCell(entry.getKey());
                table.addCell(String.valueOf(entry.getValue()));
            }
        }
        document.add(table);

        document.close();
        return baos.toByteArray();
    }
}

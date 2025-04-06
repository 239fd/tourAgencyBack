package by.bsuir.touragency.service;

import by.bsuir.touragency.dto.AnalyticsDTO;
import org.springframework.stereotype.Service;

@Service
public interface PDFExportService {
    byte[] exportAnalyticsReportToPdf(AnalyticsDTO analytics) throws Exception;
}

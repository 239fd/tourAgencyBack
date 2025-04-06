package by.bsuir.touragency.controller;

import by.bsuir.touragency.dto.AnalyticsDTO;
import by.bsuir.touragency.API.ApiResponse;
import by.bsuir.touragency.service.AnalyticsService;
import by.bsuir.touragency.service.PDFExportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@RestController
@RequestMapping("/admin/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;
    private final PDFExportService pdfExportService;

    @GetMapping("/overview")
    public ResponseEntity<ApiResponse<AnalyticsDTO>> getOverviewAnalytics() {
        AnalyticsDTO dto = analyticsService.getOverallAnalytics();
        ApiResponse<AnalyticsDTO> response = ApiResponse.<AnalyticsDTO>builder()
                .data(dto)
                .status(true)
                .message("Overall analytics retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/period")
    public ResponseEntity<ApiResponse<AnalyticsDTO>> getAnalyticsForPeriod(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Instant startInstant = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endInstant = endDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant();
        AnalyticsDTO dto = analyticsService.getAnalyticsForPeriod(startInstant, endInstant);
        ApiResponse<AnalyticsDTO> response = ApiResponse.<AnalyticsDTO>builder()
                .data(dto)
                .status(true)
                .message("Period analytics retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/report")
    public ResponseEntity<ApiResponse<AnalyticsDTO>> getReport(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        AnalyticsDTO dto;
        if (startDate != null && endDate != null) {
            Instant startInstant = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
            Instant endInstant = endDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant();
            dto = analyticsService.getAnalyticsForPeriod(startInstant, endInstant);
        } else {
            dto = analyticsService.getOverallAnalytics();
        }
        ApiResponse<AnalyticsDTO> response = ApiResponse.<AnalyticsDTO>builder()
                .data(dto)
                .status(true)
                .message("Report generated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/report/pdf")
    public ResponseEntity<ByteArrayResource> getAnalyticsReportPdf(
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        try {
            AnalyticsDTO analytics;
            if (startDate != null && endDate != null) {
                Instant startInstant = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
                Instant endInstant = endDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant();
                analytics = analyticsService.getAnalyticsForPeriod(startInstant, endInstant);
            } else {
                analytics = analyticsService.getOverallAnalytics();
            }
            byte[] pdfBytes = pdfExportService.exportAnalyticsReportToPdf(analytics);
            ByteArrayResource resource = new ByteArrayResource(pdfBytes);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=analytics_report.pdf");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(pdfBytes.length)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}

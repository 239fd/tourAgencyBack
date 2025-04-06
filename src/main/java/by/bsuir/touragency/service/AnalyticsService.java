package by.bsuir.touragency.service;

import by.bsuir.touragency.dto.AnalyticsDTO;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public interface AnalyticsService {
    AnalyticsDTO getOverallAnalytics();
    AnalyticsDTO getAnalyticsForPeriod(Instant start, Instant end);
}

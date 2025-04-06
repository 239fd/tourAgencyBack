package by.bsuir.touragency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsDTO {
    private long totalOrders;
    private long totalClients;
    private double averageAgeMale;
    private double averageAgeFemale;
    private double overallAverageAge;
    private double percentageMale;
    private double percentageFemale;
    private Map<String, Long> ordersStatusCount;
}

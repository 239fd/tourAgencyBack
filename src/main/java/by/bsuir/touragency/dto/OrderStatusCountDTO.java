package by.bsuir.touragency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusCountDTO {
    private long pending;
    private long approved;
    private long rejected;
}

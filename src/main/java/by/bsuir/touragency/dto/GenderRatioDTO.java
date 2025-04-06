package by.bsuir.touragency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenderRatioDTO {
    private double malePercentage;
    private double femalePercentage;
}

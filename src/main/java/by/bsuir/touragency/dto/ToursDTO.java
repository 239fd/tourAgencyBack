package by.bsuir.touragency.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToursDTO {
    private int amount;
    private List<TourDTO> tours;
}

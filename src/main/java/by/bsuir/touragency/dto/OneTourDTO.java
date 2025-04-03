package by.bsuir.touragency.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OneTourDTO {
    private int id;
    private String name;
    private String description;
    private int numberOfDays;
    private double price;
}

package by.bsuir.touragency.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

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
    private List<LocalDate> startDates;
}

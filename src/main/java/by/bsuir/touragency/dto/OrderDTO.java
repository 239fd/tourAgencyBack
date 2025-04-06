package by.bsuir.touragency.dto;

import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private Instant createdDateTime;
    private Instant modifiedDateTime;
    private int numberOfPeople;
    private String nameOfTour;
    private int numberOfDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

}
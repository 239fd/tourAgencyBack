package by.bsuir.touragency.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderSubmissionDTO {
    private Long tourId;
    private LocalDate startDate;
    private int numberOfPeople;
    private String specialRequest;
    private String language;
    private String name;
    private String surname;
    private String patronymic;
    private String phoneNumber;
    private String passportSeries;
    private String passportNumber;
}

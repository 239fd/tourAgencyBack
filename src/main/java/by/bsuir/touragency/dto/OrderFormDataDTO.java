package by.bsuir.touragency.dto;

import java.util.List;
import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@Builder
public class OrderFormDataDTO {
    private String tourName;
    private String country;
    private String location;
    private String description;
    private int numberOfDays;
    private double price;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<LanguageDTO> languages;
    private String name;
    private String surname;
    private String patronymic;
    private String phoneNumber;
    private String passportSeries;
    private String passportNumber;
}

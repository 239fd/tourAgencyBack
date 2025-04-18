package by.bsuir.touragency.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourDTO {

    private long id;
    private String name;
    private String country;
    private String location;
    private int numberOfDays;
    private double price;
    private String description;
    private String program;
    private List<String> languages;
    private List<String> photos;
    private LocalDate startDate;
    private LocalDate endDate;

}

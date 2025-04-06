package by.bsuir.touragency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourEditingDTO {

    private String name;
    private String country;
    private String location;
    private Integer numberOfDays;
    private Double price;
    private String description;
    private String program;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> languages;
    private List<PhotoDTO> photos;
}

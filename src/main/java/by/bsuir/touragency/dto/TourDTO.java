package by.bsuir.touragency.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourDTO {

    private String name;
    private String country;
    private String location;
    private int numberOfDays;
    private int price;

}

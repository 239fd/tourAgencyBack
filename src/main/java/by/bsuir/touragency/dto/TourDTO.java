package by.bsuir.touragency.dto;

import lombok.*;

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
    private int price;

}

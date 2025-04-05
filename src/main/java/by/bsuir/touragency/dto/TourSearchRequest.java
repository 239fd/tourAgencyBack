package by.bsuir.touragency.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourSearchRequest {
    private String keyword;
    private Double minPrice;
    private Double maxPrice;
    private Integer days;
    private Integer peopleCount;
    private String country;
    private String sortBy;
}
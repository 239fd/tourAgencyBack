package by.bsuir.touragency.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDTO {

    private String name;
    private String surname;
    private String patronymic;
    private String phoneNumber;
    private String passportSeries;
    private String passportNumber;
    private Double balance;

}
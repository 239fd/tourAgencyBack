package by.bsuir.touragency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDTO {
    private Long id;
    private String fullName;
    private String gender;
    private String phoneNumber;
    private String email;
    private String passportNumber;
    private String passportSeries;
    private LocalDate birthday;
    private int ordersCount;
}

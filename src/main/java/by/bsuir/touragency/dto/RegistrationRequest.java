package by.bsuir.touragency.dto;

import by.bsuir.touragency.entity.Enum.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@RequiredArgsConstructor
public class RegistrationRequest {
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String password;
    private int age;
    private String phone;
    private String gender;
    private Instant birthday;
    private Role role;
    private String passportNumber;
    private String passportSeries;
}

package by.bsuir.touragency.dto;

import by.bsuir.touragency.entity.Enum.Role;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String password;
    private Role role;

}
package by.bsuir.touragency.dto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class SignInRequestDto {
    private String email;
    private String password;
}
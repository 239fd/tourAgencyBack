package by.bsuir.touragency.service;


import by.bsuir.touragency.dto.ResponseDto;
import by.bsuir.touragency.dto.SignInRequestDto;
import by.bsuir.touragency.dto.SignUpRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    ResponseDto signUp(SignUpRequestDto signUpRequestDTO);
    ResponseDto signIn(SignInRequestDto requestDTO);
    boolean validateToken(String authHeader);
}
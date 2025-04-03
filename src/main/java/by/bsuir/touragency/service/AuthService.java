package by.bsuir.touragency.service;

import by.bsuir.touragency.dto.AuthResponse;
import by.bsuir.touragency.dto.LoginRequest;
import by.bsuir.touragency.dto.RegistrationRequest;

public interface AuthService {
    AuthResponse register(RegistrationRequest registrationRequest);
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse refreshToken(String refreshToken);
}

package by.bsuir.touragency.controller;

import by.bsuir.touragency.API.ApiResponse;
import by.bsuir.touragency.dto.AuthResponse;
import by.bsuir.touragency.dto.LoginRequest;
import by.bsuir.touragency.dto.RegistrationRequest;
import by.bsuir.touragency.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegistrationRequest registrationRequest) {
        AuthResponse authResponse = authService.register(registrationRequest);
        ApiResponse<AuthResponse> response = ApiResponse.<AuthResponse>builder()
                .data(authResponse)
                .status(true)
                .message("User registered successfully. Tokens generated.")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);
        ApiResponse<AuthResponse> response = ApiResponse.<AuthResponse>builder()
                .data(authResponse)
                .status(true)
                .message("User logged in successfully.")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(@RequestParam String refreshToken) {
        AuthResponse authResponse = authService.refreshToken(refreshToken);
        ApiResponse<AuthResponse> response = ApiResponse.<AuthResponse>builder()
                .data(authResponse)
                .status(true)
                .message("Token refreshed successfully.")
                .build();
        return ResponseEntity.ok(response);
    }
}

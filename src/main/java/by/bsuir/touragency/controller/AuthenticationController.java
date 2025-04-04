package by.bsuir.touragency.controller;

import by.bsuir.touragency.API.ApiResponse;
import by.bsuir.touragency.dto.ResponseDto;
import by.bsuir.touragency.dto.SignInRequestDto;
import by.bsuir.touragency.dto.SignUpRequestDto;
import by.bsuir.touragency.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    public ResponseEntity<ApiResponse<ResponseDto>> signUp(@RequestBody SignUpRequestDto requestDTO) {
        ApiResponse<ResponseDto> response = ApiResponse.<ResponseDto>builder()
                .data(authenticationService.signUp(requestDTO))
                .status(true)
                .message("Registration successful")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signIn")
    public ResponseEntity<ApiResponse<ResponseDto>> signIn(@RequestBody SignInRequestDto requestDTO) {
        ApiResponse<ResponseDto> response = ApiResponse.<ResponseDto>builder()
                .data(authenticationService.signIn(requestDTO))
                .status(true)
                .message("Login successful")
                .build();
        return ResponseEntity.ok(response);
    }


    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.clearContext();
        }

        ApiResponse<String> response = ApiResponse.<String>builder()
                .data("You have been logged out")
                .status(true)
                .message("Logout successful")
                .build();
        return ResponseEntity.ok(response);
    }


    @PostMapping("/validateToken")
    public ResponseEntity<ApiResponse<String>> validateToken(@RequestHeader("Authorization") String authHeader) {
        boolean isValid = authenticationService.validateToken(authHeader);

        if (isValid) {
            ApiResponse<String> response = ApiResponse.<String>builder()
                    .data("Valid token")
                    .status(true)
                    .message("Token is valid")
                    .build();
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<String> response = ApiResponse.<String>builder()
                    .data("Invalid or expired token")
                    .status(false)
                    .message("Unauthorized")
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

}
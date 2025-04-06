package by.bsuir.touragency.controller;

import by.bsuir.touragency.API.ApiResponse;
import by.bsuir.touragency.dto.BalanceDTO;
import by.bsuir.touragency.dto.BalanceTopUpDTO;
import by.bsuir.touragency.dto.UserProfileDTO;
import by.bsuir.touragency.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/balance")
    public ResponseEntity<ApiResponse<BalanceDTO>> topUpBalance(@RequestBody BalanceTopUpDTO request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        BalanceDTO balanceDTO = userService.topUpBalance(email, request.getAmount());

        ApiResponse<BalanceDTO> response = ApiResponse.<BalanceDTO>builder()
                .data(balanceDTO)
                .status(true)
                .message("Balance successfully changed")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<UserProfileDTO>> getUserInfo() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserProfileDTO userProfile = userService.getProfile(email);

        ApiResponse<UserProfileDTO> response = ApiResponse.<UserProfileDTO>builder()
                .data(userProfile)
                .status(true)
                .message("User profile retrieved")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Void>> updateUserInfo(@RequestBody UserProfileDTO userProfileDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        userService.updateProfile(email, userProfileDTO);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(true)
                .message("User profile updated")
                .build();
        return ResponseEntity.ok(response);
    }
}

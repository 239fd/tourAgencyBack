package by.bsuir.touragency.service.impl;

import by.bsuir.touragency.dto.ResponseDto;
import by.bsuir.touragency.dto.SignInRequestDto;
import by.bsuir.touragency.dto.SignUpRequestDto;
import by.bsuir.touragency.entity.Enum.Role;
import by.bsuir.touragency.entity.Users;
import by.bsuir.touragency.exceptions.UserException;
import by.bsuir.touragency.repository.UserRepository;
import by.bsuir.touragency.service.AuthenticationService;
import by.bsuir.touragency.service.JWTService;
import io.jsonwebtoken.Claims;
import java.util.Date;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseDto signUp(SignUpRequestDto requestDTO) {
        Optional<Users> existingUser = userRepository.findByEmail(requestDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this email already exists");
        }

        Role role = requestDTO.getRole() != null ? requestDTO.getRole() : Role.USER;
        Users user = Users.builder()
                .name(requestDTO.getName())
                .active(true)
                .surname(requestDTO.getSurname())
                .patronymic(requestDTO.getPatronymic())
                .email(requestDTO.getEmail())
                .balance(1000.00)
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);
        String token = jwtService.generateAccessToken(user);

        return ResponseDto.builder()
                .accessToken(token)
                .userId(user.getId())
                .role(user.getRole().name())
                .build();
    }


    @Override
    public ResponseDto signIn(SignInRequestDto requestDTO) {
        Optional<Users> userOpt = userRepository.findByEmail(requestDTO.getEmail());

        if (userOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this email does not exist");
        }

        Users user = userOpt.get();

        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password");
        }

        String token = jwtService.generateAccessToken(user);

        return ResponseDto.builder()
                .accessToken(token)
                .userId(user.getId())
                .role(user.getRole().name())
                .build();
    }


    @Override
    public boolean validateToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UserException("Missing or invalid Authorization header");
        }
        String token = authHeader.replace("Bearer ", "");
        try {
            Claims claims = jwtService.extractAllClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
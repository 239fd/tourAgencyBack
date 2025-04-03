package by.bsuir.touragency.service.impl;

import by.bsuir.touragency.dto.AuthResponse;
import by.bsuir.touragency.dto.LoginRequest;
import by.bsuir.touragency.dto.RegistrationRequest;
import by.bsuir.touragency.entity.RefreshToken;
import by.bsuir.touragency.entity.Users;
import by.bsuir.touragency.exceptions.AuthenticationException;
import by.bsuir.touragency.exceptions.EmailAlreadyExistsException;
import by.bsuir.touragency.exceptions.PassportAlreadyExistsException;
import by.bsuir.touragency.exceptions.PhoneAlreadyExistsException;
import by.bsuir.touragency.repository.RefreshTokenRepository;
import by.bsuir.touragency.repository.UserRepository;
import by.bsuir.touragency.config.JwtTokenProvider;
import by.bsuir.touragency.mappers.UserMapper;
import by.bsuir.touragency.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public AuthResponse register(RegistrationRequest registrationRequest) {
        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("User with email " + registrationRequest.getEmail() + " already exists");
        }
        if (userRepository.existsByPassportSeriesAndPassportNumber(
                registrationRequest.getPassportSeries(), registrationRequest.getPassportNumber())) {
            throw new PassportAlreadyExistsException("Passport details are already in use");
        }
        if (userRepository.existsByPhoneNumber(registrationRequest.getPhone())) {
            throw new PhoneAlreadyExistsException("Phone number is already in use");
        }

        Users user = userMapper.registrationRequestToUser(registrationRequest);

        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setActive(true);
        user.setPhoneNumber(registrationRequest.getPhone());

        userRepository.save(user);

        String accessToken = jwtTokenProvider.generateAccessToken(registrationRequest.getEmail());
        String refreshTokenStr = jwtTokenProvider.generateRefreshToken(registrationRequest.getEmail());

        Optional<RefreshToken> existingTokenOpt = refreshTokenRepository.findByUser(user);
        if(existingTokenOpt.isPresent()){
            RefreshToken existingToken = existingTokenOpt.get();
            existingToken.setToken(refreshTokenStr);
            existingToken.setExpiryDate(Instant.now().plusMillis(jwtTokenProvider.getRefreshExpirationMs()));
            refreshTokenRepository.save(existingToken);
        } else {
            RefreshToken refreshToken = RefreshToken.builder()
                    .token(refreshTokenStr)
                    .expiryDate(Instant.now().plusMillis(jwtTokenProvider.getRefreshExpirationMs()))
                    .user(user)
                    .build();
            refreshTokenRepository.save(refreshToken);
        }

        return new AuthResponse(accessToken, refreshTokenStr);
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        } catch (Exception e) {
            throw new AuthenticationException("Invalid email/password supplied");
        }

        String accessToken = jwtTokenProvider.generateAccessToken(loginRequest.getEmail());
        String refreshTokenStr = jwtTokenProvider.generateRefreshToken(loginRequest.getEmail());

        Users user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AuthenticationException("User not found"));

        Optional<RefreshToken> existingTokenOpt = refreshTokenRepository.findByUser(user);
        if(existingTokenOpt.isPresent()){
            RefreshToken existingToken = existingTokenOpt.get();
            existingToken.setToken(refreshTokenStr);
            existingToken.setExpiryDate(Instant.now().plusMillis(jwtTokenProvider.getRefreshExpirationMs()));
            refreshTokenRepository.save(existingToken);
        } else {
            RefreshToken refreshToken = RefreshToken.builder()
                    .token(refreshTokenStr)
                    .expiryDate(Instant.now().plusMillis(jwtTokenProvider.getRefreshExpirationMs()))
                    .user(user)
                    .build();
            refreshTokenRepository.save(refreshToken);
        }

        return new AuthResponse(accessToken, refreshTokenStr);
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            throw new AuthenticationException("Invalid refresh token");
        }
        String username = jwtTokenProvider.getUsernameFromAccessToken(refreshToken);
        String newAccessToken = jwtTokenProvider.generateAccessToken(username);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(username);

        Users user = userRepository.findByEmail(username)
                .orElseThrow(() -> new AuthenticationException("User not found"));

        refreshTokenRepository.deleteByUser(user);
        RefreshToken rt = RefreshToken.builder()
                .token(newRefreshToken)
                .expiryDate(Instant.now().plusMillis(jwtTokenProvider.getRefreshExpirationMs()))
                .user(user)
                .build();
        refreshTokenRepository.save(rt);

        return new AuthResponse(newAccessToken, newRefreshToken);
    }
}

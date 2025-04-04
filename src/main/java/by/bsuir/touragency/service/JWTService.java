package by.bsuir.touragency.service;

import by.bsuir.touragency.entity.Users;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JWTService {
    String generateAccessToken(Users user);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    Claims extractAllClaims(String token);
    String extractUsername(String jwt);
    boolean isTokenValid(String token, UserDetails userDetails);
}
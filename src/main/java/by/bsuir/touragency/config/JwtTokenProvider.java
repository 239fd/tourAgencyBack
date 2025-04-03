package by.bsuir.touragency.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.access.secret}")
    private String accessTokenSecret;

    @Value("${jwt.refresh.secret}")
    private String refreshTokenSecret;

    @Value("${jwt.access.expiration-ms}")
    private Long accessTokenExpirationMs;

    @Value("${jwt.refresh.expiration-ms}")
    private Long refreshTokenExpirationMs;

    private Key getAccessSigningKey() {
        return Keys.hmacShaKeyFor(accessTokenSecret.getBytes(StandardCharsets.UTF_8));
    }

    private Key getRefreshSigningKey() {
        return Keys.hmacShaKeyFor(refreshTokenSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpirationMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getAccessSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefreshToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenExpirationMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getRefreshSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromAccessToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getAccessSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateAccessToken(String token) {
        return validateToken(token, getAccessSigningKey());
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, getRefreshSigningKey());
    }

    private boolean validateToken(String token, Key key) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }
    public Long getRefreshExpirationMs() {
        return refreshTokenExpirationMs;
    }

}

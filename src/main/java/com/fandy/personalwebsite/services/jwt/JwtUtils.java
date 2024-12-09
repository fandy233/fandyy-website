package com.fandy.personalwebsite.services.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.Claims;

@Component
public class JwtUtils {

    @Value("${jwt.refreshTokenExpMs}")
    private long refreshTokenExpirationMs; //1 day

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;  //15 min

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // Get expiry date of a token
    public Date getTokenExpiry(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration();
    }

    //Generate token
    public String generateJwtToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    // Generate refresh token
    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    // Get expiry date of a refresh token
    public Date getRefreshTokenExpiry(String refreshToken) {
        return getTokenExpiry(refreshToken);
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.err.println("JWT validation failed: " + e.getMessage());
        }
        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            Date date = getRefreshTokenExpiry(refreshToken);
            return !date.before(new Date());
        } catch (Exception e) {
            return false; // Token is invalid or expired
        }
    }

}

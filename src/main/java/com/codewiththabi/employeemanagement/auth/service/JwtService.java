package com.codewiththabi.employeemanagement.auth.service;

import com.codewiththabi.employeemanagement.auth.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final String secret = "my-super-secret-key-that-is-at-least-32-characters-long";

    private final SecretKey key = Keys.hmacShaKeyFor((secret.getBytes(StandardCharsets.UTF_8)));

    private final long expiration = 1000 * 60 + 60;

    public String generateToken(User user) {

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public String exactEmail(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}

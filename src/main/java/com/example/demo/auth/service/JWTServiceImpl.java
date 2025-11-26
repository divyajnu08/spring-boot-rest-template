package com.example.demo.auth.service;

import com.example.demo.auth.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JWTServiceImpl implements JWTService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expiration;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user) {

        long now = System.currentTimeMillis();

        JwtBuilder b = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expiration))
                .claim("roles", user.getRoles());
        return b.signWith(key, SignatureAlgorithm.HS256).compact();
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public Set<String> extractRoles(String token) {
        Claims c = parseClaims(token);
        Object rolesObj = c.get("roles");
        if (rolesObj instanceof java.util.List<?> list) {
            return list.stream().map(Object::toString).collect(Collectors.toSet());
        }
        return Set.of();
    }

    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}

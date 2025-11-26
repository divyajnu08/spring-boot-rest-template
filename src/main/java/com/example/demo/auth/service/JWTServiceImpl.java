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

/**
 * Implementation of {@link JWTService} responsible for generating, parsing,
 * and validating JSON Web Tokens (JWT) within the application.
 * <p>
 * This service uses the {@code io.jsonwebtoken} (JJWT) library to:
 * <ul>
 *     <li>Create signed JWT tokens containing user information</li>
 *     <li>Extract claims such as username</li>
 *     <li>Validate token signature and expiration</li>
 * </ul>
 * </p>
 *
 * <p>
 * The secret key and expiration time are loaded from application properties:
 * <ul>
 *     <li><code>jwt.secret</code> – the HMAC signing key</li>
 *     <li><code>jwt.expiration-ms</code> – token lifetime in milliseconds</li>
 * </ul>
 * </p>
 */
@Service
public class JWTServiceImpl implements JWTService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expiration;

    /**
     * Parsed SecretKey generated from the configured secret.
     */
    private SecretKey key;

    /**
     * Initializes the signing key after dependency injection.
     * <p>
     * Converts the configured secret string into an HMAC SHA key compatible
     * with the JJWT library.
     * </p>
     */
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generates a JWT token for the given user.
     * <p>
     * The token includes:
     * </p>
     * <ul>
     *     <li>The user's phone number as the <strong>subject</strong></li>
     *     <li>Issued-at timestamp</li>
     *     <li>Expiration timestamp</li>
     *     <li>Assigned roles as a claim</li>
     * </ul>
     *
     * @param user the authenticated user
     * @return a signed JWT string
     */
    @Override
    public String generateToken(User user) {

        long now = System.currentTimeMillis();

        JwtBuilder builder = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expiration))
                .claim("roles", user.getRoles());

        return builder
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the username (phone number) from the token’s "sub" claim.
     *
     * @param token the JWT token
     * @return the username stored in the token
     */
    @Override
    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * Validates a JWT token by checking:
     * <ul>
     *     <li>Signature validity</li>
     *     <li>Token format</li>
     *     <li>Expiration date</li>
     * </ul>
     *
     * @param token the token to check
     * @return {@code true} if valid, {@code false} otherwise
     */
    @Override
    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }

    /**
     * Parses and returns the JWT claims.
     *
     * @param token the JWT token
     * @return the decoded {@link Claims}
     * @throws JwtException if the token is invalid or expired
     */
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

package com.example.demo.auth.service;

import com.example.demo.auth.model.User;

/**
 * Service interface for handling JSON Web Token (JWT) operations.
 * <p>
 * Implementations of this interface are responsible for:
 * <ul>
 *     <li>Generating JWT tokens for authenticated users</li>
 *     <li>Extracting information from tokens</li>
 *     <li>Validating token integrity and expiration</li>
 * </ul>
 * </p>
 */
public interface JWTService {

    /**
     * Generates a JWT token for the given user.
     *
     * @param user the authenticated user for whom the token is generated
     * @return a signed JWT string representing the user identity and claims
     */
    String generateToken(User user);

    /**
     * Extracts the username (phone number in this application)
     * from the provided JWT token.
     *
     * @param token the JWT token from which to extract the username
     * @return the embedded username, or {@code null} if extraction fails
     */
    String extractUsername(String token);

    /**
     * Validates the provided JWT token.
     * <p>
     * A token is considered valid if:
     * </p>
     * <ul>
     *     <li>Its signature is valid</li>
     *     <li>It has not expired</li>
     *     <li>It contains expected claims</li>
     * </ul>
     *
     * @param token the token to validate
     * @return {@code true} if the token is valid; {@code false} otherwise
     */
    boolean isTokenValid(String token);
}

package com.example.demo.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents the response returned after successful authentication,
 * containing the generated JSON Web Token (JWT).
 *
 * <p>This DTO is typically returned from login or OTP verification
 * endpoints to allow the client to use the token for subsequent
 * authenticated requests.</p>
 */
@Data
@AllArgsConstructor
public class JwtResponse {

    /**
     * The JSON Web Token generated for the authenticated user.
     */
    private String jwt;
}

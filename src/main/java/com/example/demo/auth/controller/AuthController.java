package com.example.demo.auth.controller;

import com.example.demo.auth.dto.JwtResponse;
import com.example.demo.auth.dto.LoginRequest;
import com.example.demo.auth.dto.OtpVerifyRequest;
import com.example.demo.auth.service.JWTServiceImpl;
import com.example.demo.auth.service.OtpAuthServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller responsible for handling authentication requests.
 *
 * <p>This controller provides two main functionalities:
 * <ul>
 *     <li>Login using username → returns JWT (mock login, no password validation yet).</li>
 *     <li>OTP verification using dynamic provider selection (e.g., Firebase, Twilio).</li>
 * </ul>
 *
 * <p>The OTP verification logic is delegated to {@link OtpAuthServiceImpl},
 * which uses the Adapter Pattern to support multiple OTP providers
 * without changing backend logic.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JWTServiceImpl jwtService;
    private final OtpAuthServiceImpl otpAuthServiceImpl;

    /**
     * Constructor injection for required services.
     *
     * @param jwtService         Utility class for generating JWT tokens.
     * @param otpAuthServiceImpl Service responsible for OTP verification.
     */
    public AuthController(JWTServiceImpl jwtService, OtpAuthServiceImpl otpAuthServiceImpl) {
        this.jwtService = jwtService;
        this.otpAuthServiceImpl = otpAuthServiceImpl;
    }

    /**
     * Mock login API — currently accepts only username and
     * returns a JWT token without validating credentials.
     *
     * @param request Login request containing username.
     * @return ResponseEntity with JWT token as plain text.
     * @apiNote Replace with real authentication logic in future.
     */
    /*@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = jwtService.generateToken(request.getUsername());
        return ResponseEntity.ok(token);
    }*/

    /**
     * Verifies OTP using the configured provider and returns a JWT token.
     *
     * @param request Contains providerKey, providerToken, and optional meta data.
     * @return ResponseEntity containing a JWT wrapped in {@link JwtResponse}.
     * @throws IllegalArgumentException If invalid providerKey is passed.
     * @throws RuntimeException         If OTP verification fails.
     * @apiNote This endpoint uses dynamic provider selection
     * (Adapter Pattern) to remain independent of the OTP provider.
     */
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpVerifyRequest request) {
        String token = otpAuthServiceImpl.verifyAndGenerateToken(
                request.getProviderKey(),
                request.getProviderToken(),
                request.getMeta()
        );
        return ResponseEntity.ok(new JwtResponse(token));
    }
}

package com.example.demo.controller;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.OtpVerifyRequest;
import com.example.demo.service.JWTService;
import com.example.demo.service.OtpAuthService;
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
 * <p>The OTP verification logic is delegated to {@link OtpAuthService},
 * which uses the Adapter Pattern to support multiple OTP providers
 * without changing backend logic.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JWTService jwtService;
    private final OtpAuthService otpAuthService;

    /**
     * Constructor injection for required services.
     *
     * @param jwtService     Utility class for generating JWT tokens.
     * @param otpAuthService Service responsible for OTP verification.
     */
    public AuthController(JWTService jwtService, OtpAuthService otpAuthService) {
        this.jwtService = jwtService;
        this.otpAuthService = otpAuthService;
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
        String token = otpAuthService.verifyAndGenerateToken(
                request.getProviderKey(),
                request.getProviderToken(),
                request.getMeta()
        );
        return ResponseEntity.ok(new JwtResponse(token));
    }
}

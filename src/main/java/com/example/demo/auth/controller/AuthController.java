package com.example.demo.auth.controller;

import com.example.demo.auth.dto.JwtResponse;
import com.example.demo.auth.dto.OtpVerifyRequest;
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

    private final OtpAuthServiceImpl otpAuthServiceImpl;

    /**
     * Constructor injection for required services.
     *
     * @param otpAuthServiceImpl Service responsible for OTP verification.
     */
    public AuthController(OtpAuthServiceImpl otpAuthServiceImpl) {
        this.otpAuthServiceImpl = otpAuthServiceImpl;
    }

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

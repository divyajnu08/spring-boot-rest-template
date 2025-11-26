package com.example.demo.auth.service;

import com.example.demo.auth.adapter.OtpProviderAdapter;
import com.example.demo.auth.dto.ProviderVerifyResult;
import com.example.demo.auth.model.User;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Service responsible for OTP verification and token generation.
 *
 * <p>This class uses the Adapter Pattern for OTP providers (e.g., Firebase, Twilio, AWS SNS).
 * The actual provider is chosen dynamically using {@code providerKey}, allowing the backend to remain
 * independent of any specific OTP provider implementation.
 *
 * <p>Once the OTP is successfully verified, the service returns an authentication token (JWT in real implementation).
 *
 * <p>NOTE: Currently returns a test token for development purposes.
 * In production, replace with actual JWT generation logic.
 */
@Service
public class OtpAuthServiceImpl implements OtpAuthService {

    /**
     * Registry of available OTP provider adapters.
     * The key represents the provider's unique identifier (e.g., "FIREBASE", "TWILIO").
     */
    @NonNull
    private final Map<String, OtpProviderAdapter> otpProviderRegistry;
    private final JWTServiceImpl jwtService;
    private final UserService userService;

    /**
     * Constructor-based dependency injection.
     *
     * @param otpProviderRegistry a map containing available OTP providers
     */
    public OtpAuthServiceImpl(@NonNull Map<String, OtpProviderAdapter> otpProviderRegistry, JWTServiceImpl jwtService, UserDetailsByPhoneService userDetailsByPhoneService, UserService userService) {
        this.otpProviderRegistry = otpProviderRegistry;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    /**
     * Verifies OTP using the selected provider and generates an authentication token upon success.
     *
     * @param providerKey   Unique key to identify which OTP provider should process the request.
     *                      Must match the key returned by {@code providerKey()} in each adapter.
     * @param providerToken Token/OTP received from the frontend and verified using an external provider.
     * @param meta          Optional metadata that may be required by some providers
     *                      (e.g., phone number, requestId). Can be {@code null}.
     * @return A test authentication token (to be replaced with JWT).
     * @throws IllegalArgumentException if providerKey does not match any registered provider.
     * @throws RuntimeException         if OTP verification fails (provider returns false).
     */
    @Override
    public String verifyAndGenerateToken(
            @NonNull String providerKey,
            @NonNull String providerToken,
            @Nullable Map<String, Object> meta
    ) {
        OtpProviderAdapter adapter = otpProviderRegistry.get(providerKey);

        if (adapter == null) {
            throw new IllegalArgumentException("Unknown OTP provider: " + providerKey);
        }

        ProviderVerifyResult result = adapter.verify(providerToken, meta);
        if (!result.isSuccess()) {
            throw new RuntimeException("OTP verification failed!");
        }

        String phoneNumber = result.getPhoneNumber();
        if (phoneNumber == null) {
            throw new RuntimeException("OTP provider did not return a phone number!");
        }

        User user = userService.findOrCreateUserByPhoneNumber(phoneNumber);
        return jwtService.generateToken(user);
    }
}

package com.example.demo.auth.service;

import com.example.demo.auth.adapter.OtpProviderAdapter;
import com.example.demo.auth.dto.ProviderVerifyResult;
import com.example.demo.auth.model.User;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Service responsible for performing OTP verification and generating authentication tokens.
 *
 * <p>This service uses a registry of {@link OtpProviderAdapter} instances, following the
 * Adapter Pattern to allow seamless switching between OTP providers such as Firebase,
 * Twilio, AWS SNS, or others. The actual provider is selected dynamically based on
 * {@code providerKey}, keeping the authentication flow provider-agnostic.</p>
 *
 * <p>Once OTP verification succeeds, a user record is retrieved or created
 * (depending on application rules), and a JWT token is generated for the user.</p>
 */
@Service
public class OtpAuthServiceImpl implements OtpAuthService {

    /**
     * Registry mapping provider keys to their respective OTP adapters.
     * <p>Keys must match the value returned by {@link OtpProviderAdapter#providerKey()}.</p>
     */
    @NonNull
    private final Map<String, OtpProviderAdapter> otpProviderRegistry;

    private final JWTServiceImpl jwtService;
    private final UserService userService;

    /**
     * Constructs an instance of {@code OtpAuthServiceImpl}.
     *
     * @param otpProviderRegistry map of available OTP providers
     * @param jwtService          service for generating JWT tokens
     * @param userService         service for managing and retrieving user records
     */
    public OtpAuthServiceImpl(
            @NonNull Map<String, OtpProviderAdapter> otpProviderRegistry,
            JWTServiceImpl jwtService,
            UserService userService
    ) {
        this.otpProviderRegistry = otpProviderRegistry;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    /**
     * Verifies an OTP token via the selected provider and generates a JWT authentication token
     * if verification succeeds.
     *
     * <p>Workflow:</p>
     * <ol>
     *     <li>Resolve the provider using {@code providerKey}</li>
     *     <li>Call {@link OtpProviderAdapter#verify(String, Map)} to validate the token</li>
     *     <li>Ensure the provider returned a phone number</li>
     *     <li>Retrieve or create a user with that phone number</li>
     *     <li>Generate a JWT token representing the authenticated user</li>
     * </ol>
     *
     * @param providerKey   the identifier of the OTP provider to use
     * @param providerToken the OTP or verification token to validate
     * @param meta          optional metadata required by the OTP provider (e.g., requestId, phoneNumber)
     * @return a JWT token if verification succeeds
     * @throws IllegalArgumentException if {@code providerKey} does not match any provider
     * @throws RuntimeException         if OTP verification fails or the provider does not return a phone number
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

        User user = userService.findOrCreateUserByPhoneNumber(phoneNumber, meta);
        return jwtService.generateToken(user);
    }
}

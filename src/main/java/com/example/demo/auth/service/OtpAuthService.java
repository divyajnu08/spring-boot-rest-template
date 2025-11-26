package com.example.demo.auth.service;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Map;

/**
 * Service interface responsible for handling OTP-based authentication workflows.
 * <p>
 * Implementations of this interface are expected to:
 * <ul>
 *     <li>Delegate OTP verification to the appropriate provider</li>
 *     <li>Validate provider responses</li>
 *     <li>Generate a JWT or other authentication token upon successful verification</li>
 * </ul>
 * </p>
 */
public interface OtpAuthService {

    /**
     * Verifies an OTP using a specific provider and, if successful,
     * generates an authentication token for the user.
     * <p>
     * Typical workflow:
     * </p>
     * <ol>
     *     <li>Look up the provider using the given provider key</li>
     *     <li>Verify the OTP using the provider's adapter</li>
     *     <li>If verification succeeds:
     *         <ul>
     *             <li>Create or retrieve the corresponding application user</li>
     *             <li>Generate a JWT token for authenticated access</li>
     *         </ul>
     *     </li>
     * </ol>
     *
     * @param providerKey   identifier for the OTP provider (e.g., "firebase", "twilio")
     * @param providerToken the token or OTP code to be verified by the provider
     * @param meta          optional provider-specific metadata (e.g., phone number, session ID)
     * @return a newly generated authentication token (e.g., JWT) if verification succeeds
     * @throws RuntimeException if verification fails or provider is unknown
     */
    String verifyAndGenerateToken(
            @NonNull String providerKey,
            @NonNull String providerToken,
            @Nullable Map<String, Object> meta
    );
}

package com.example.demo.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * Represents the result returned by an OTP provider after attempting to verify
 * a submitted OTP or verification token.
 * <p>
 * This DTO encapsulates both the outcome of the verification process and any
 * additional user/provider-specific information needed by the application.
 * </p>
 */
@Data
@AllArgsConstructor
public class ProviderVerifyResult {

    /**
     * Indicates whether the OTP verification was successful.
     * <p>
     * If {@code true}, the provider has confirmed the supplied verification token.
     * If {@code false}, verification failed due to invalid/expired token or other errors.
     * </p>
     */
    private boolean success;

    /**
     * A unique user identifier obtained from the provider (if available).
     * <p>
     * Some providers, such as Firebase, return a user UID upon successful verification.
     * This may be {@code null} for providers that do not link verification to a user ID.
     * </p>
     */
    private String userId;

    /**
     * The phone number associated with the verification attempt.
     * <p>
     * This value is often normalized by the provider (e.g., E.164 format).
     * </p>
     */
    private String phoneNumber;

    /**
     * Additional metadata returned by the OTP provider.
     * <p>
     * This may contain provider-specific fields such as:
     * <ul>
     *     <li>verification timestamps</li>
     *     <li>provider session information</li>
     *     <li>custom validation attributes</li>
     * </ul>
     * This field is optional and provider-dependent.
     * </p>
     */
    private Map<String, Object> metadata;
}

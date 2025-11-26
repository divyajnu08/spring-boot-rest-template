package com.example.demo.auth.dto;

import lombok.Data;

import java.util.Map;

/**
 * Request payload used for verifying a One-Time Password (OTP).
 * <p>
 * This DTO is sent by the client when attempting to validate an OTP
 * received via a specific provider (e.g., Twilio, Firebase, custom provider).
 * The server uses this information to delegate the verification process
 * to the correct {@code OtpProviderAdapter}.
 * </p>
 */
@Data
public class OtpVerifyRequest {

    /**
     * A unique key identifying which OTP provider should handle the verification.
     * <p>
     * Example values:
     * <ul>
     *     <li>"twilio"</li>
     *     <li>"firebase"</li>
     *     <li>"custom-provider"</li>
     * </ul>
     * </p>
     */
    private String providerKey;

    /**
     * The token, code, or verification value returned by the OTP provider
     * (e.g., the OTP code entered by the user or a provider-issued session token).
     */
    private String providerToken;

    /**
     * Additional metadata required by specific OTP providers.
     * <p>
     * This may include values like:
     * <ul>
     *     <li>phoneNumber</li>
     *     <li>sessionId</li>
     *     <li>client-specific validation data</li>
     * </ul>
     * This field is optional and varies depending on the provider implementation.
     * </p>
     */
    private Map<String, Object> meta;
}

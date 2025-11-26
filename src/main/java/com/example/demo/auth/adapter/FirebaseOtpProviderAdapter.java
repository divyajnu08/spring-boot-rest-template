package com.example.demo.auth.adapter;

import com.example.demo.auth.dto.ProviderVerifyResult;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Mock implementation of OTPProviderAdapter for Firebase.
 * <p>
 * This class simulates OTP verification with Firebase,
 * but does not actually call Firebase APIs yet.
 * It always returns a successful mock response.
 * <p>
 * TODO: In future, integrate real Firebase Admin SDK verification logic.
 */
@Component
public class FirebaseOtpProviderAdapter implements OtpProviderAdapter {

    /**
     * Mock OTP verification method.
     * Currently returns a hardcoded successful response.
     *
     * @param providerToken The token/OTP received from the frontend.
     *                      This would normally be validated using Firebase API.
     * @param meta          Optional metadata (e.g., phone number, request ID).
     *                      Can be null.
     * @return ProviderVerifyResult containing mock verification result.
     */
    @Override
    public ProviderVerifyResult verify(String providerToken, Map<String, Object> meta) {
        // TODO: Replace mock data with actual Firebase verification logic.
        return new ProviderVerifyResult(
                true,                 // Simulating successful verification
                "123456",             // Mock Firebase UID
                "9871188869",         // Mock phone number
                null                  // No metadata for now
        );
    }

    /**
     * Unique key used to identify this provider.
     * Registered in ProviderRegistry so runtime selection works.
     *
     * @return "FIREBASE" as provider identifier.
     */
    @Override
    public String providerKey() {
        return "FIREBASE";
    }
}

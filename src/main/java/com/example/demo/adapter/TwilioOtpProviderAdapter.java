package com.example.demo.adapter;

import com.example.demo.dto.ProviderVerifyResult;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Mock implementation of {@link OtpProviderAdapter} for Twilio.
 *
 * <p>
 * Currently returns a fake successful verification result.
 * This allows the backend OTP flow to work without actual Twilio integration.
 * </p>
 *
 * <p>
 * TODO: In the future, integrate with Twilio Verify API:
 * <a href="https://www.twilio.com/docs/verify/api">Twilio Verify API Docs</a>
 * </p>
 */
@Component
public class TwilioOtpProviderAdapter implements OtpProviderAdapter {

    /**
     * Mock OTP verification method for Twilio.
     * <p>
     * Accepts OTP/token received from frontend,
     * but returns a hardcoded successful response.
     *
     * @param providerToken OTP/token sent from the frontend.
     * @param meta          Optional metadata (could include phone number, channel, requestId)
     * @return ProviderVerifyResult - Standard result object (mocked)
     */
    @Override
    public ProviderVerifyResult verify(String providerToken, Map<String, Object> meta) {
        // TODO: Replace with actual Twilio verification logic using REST API/SDK

        return new ProviderVerifyResult(
                true,                      // Pretend verification is successful
                "user-0001",                // Mock user ID
                "0000000000",               // Mock phone number
                meta                       // Return metadata for debugging
        );
    }

    /**
     * Unique provider key used for runtime adapter selection.
     *
     * @return "TWILIO" as implementation key.
     */
    @Override
    public String providerKey() {
        return "TWILIO";
    }
}

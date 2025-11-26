package com.example.demo.auth.config;

import com.example.demo.auth.adapter.OtpProviderAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Configuration class responsible for registering OTP provider adapters.
 * <p>
 * This config collects all Spring-managed beans of type {@link OtpProviderAdapter}
 * and builds a registry (Map) that can be used to access providers by their
 * unique provider key.
 * </p>
 *
 * <p>
 * Usage example:
 * <pre>
 *     @Autowired
 *     private Map&lt;String, OtpProviderAdapter&gt; otpProviderRegistry;
 *
 *     public void sendOtp(String providerKey, String phone) {
 *         OtpProviderAdapter provider = otpProviderRegistry.get(providerKey);
 *         provider.sendOtp(phone);
 *     }
 * </pre>
 * </p>
 */
@Configuration
public class ProviderRegistryConfig {

    /**
     * Builds a registry map of all available {@link OtpProviderAdapter} beans in the application context.
     * <p>
     * Each provider supplies its own unique key via {@link OtpProviderAdapter#providerKey()}.
     * The resulting map allows efficient lookup of providers by key.
     * </p>
     *
     * @param providers all OTP provider beans automatically injected by Spring
     * @return a map where keys are provider identifiers and values are provider instances
     */
    @Bean
    public Map<String, OtpProviderAdapter> otpProviderRegistry(OtpProviderAdapter... providers) {

        return Arrays.stream(providers)
                .collect(Collectors.toMap(
                        OtpProviderAdapter::providerKey,
                        provider -> provider
                ));
    }
}

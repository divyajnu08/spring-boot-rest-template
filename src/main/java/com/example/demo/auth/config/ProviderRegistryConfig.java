package com.example.demo.auth.config;

import com.example.demo.auth.adapter.OtpProviderAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class ProviderRegistryConfig {

    @Bean
    public Map<String, OtpProviderAdapter> otpProviderRegistry(OtpProviderAdapter... providers) {

        return Arrays.stream(providers)
                .collect(Collectors.toMap(
                        OtpProviderAdapter::providerKey,
                        provider -> provider
                ));
    }
}
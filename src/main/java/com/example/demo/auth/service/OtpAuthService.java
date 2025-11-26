package com.example.demo.auth.service;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public interface OtpAuthService {
    String verifyAndGenerateToken(@NonNull String providerKey, @NonNull String providerToken, @Nullable Map<String, Object> meta);
}

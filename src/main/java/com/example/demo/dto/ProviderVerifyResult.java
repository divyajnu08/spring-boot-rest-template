package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ProviderVerifyResult {
    private boolean success;
    private String userId;
    private String phoneNumber;
    private Map<String, Object> metadata;
}

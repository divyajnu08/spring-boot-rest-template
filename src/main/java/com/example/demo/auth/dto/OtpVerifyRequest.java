package com.example.demo.auth.dto;

import lombok.Data;

import java.util.Map;

@Data
public class OtpVerifyRequest {
    private String providerKey;
    private String providerToken;
    private Map<String, Object> meta;
}
package com.example.demo.auth.service;

import com.example.demo.auth.model.User;

import java.util.Set;

public interface JWTService {

    String generateToken(User user);

    String extractUsername(String token);

    boolean isTokenValid(String token);
}

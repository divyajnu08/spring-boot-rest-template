package com.example.demo.auth.controller;

import com.example.demo.auth.model.UserProfile;
import com.example.demo.auth.service.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@Slf4j
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/{userId}/me")
    public ResponseEntity<@NonNull UserProfile> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userProfileService.getUserProfile(userId));
    }

    @PostMapping("/create")
    public ResponseEntity<@NonNull String> createUserProfile(@RequestBody UserProfile userProfile) {
        try {
            userProfileService.createUserProfile(userProfile);
        } catch (Exception e) {
            log.error("Error in creating user profile", e);
        }
        return ResponseEntity.ok("Error in creating User profile");
    }
}

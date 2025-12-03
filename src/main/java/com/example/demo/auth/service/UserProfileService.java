package com.example.demo.auth.service;

import com.example.demo.auth.dto.UserProfileDto;
import com.example.demo.auth.model.UserProfile;
import com.example.demo.auth.repository.UserProfileRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public @Nullable UserProfile getUserProfile(Long userId) {
        return userProfileRepository.findByUserId(userId).orElse(null);
    }

    public void createUserProfile(UserProfile userProfile) {
        userProfileRepository.save(userProfile);
    }
}

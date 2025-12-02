package com.example.demo.auth.repository;

import com.example.demo.auth.model.UserProfile;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<@NonNull UserProfile, @NonNull Long> {

    @NonNull Optional<UserProfile> findById(Long id);

    Optional<UserProfile> findByUserId(Long userId);

}

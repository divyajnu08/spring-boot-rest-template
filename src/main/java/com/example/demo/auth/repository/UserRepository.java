package com.example.demo.auth.repository;

import com.example.demo.auth.model.User;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<@NonNull User, @NonNull Long> {
    Optional<User> findByPhoneNumber(String phoneNumber);
}

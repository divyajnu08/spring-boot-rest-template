package com.example.demo.auth.repository;

import com.example.demo.auth.model.Address;
import com.example.demo.auth.model.UserProfile;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<@NonNull Address, @NonNull Long> {

    @NonNull Optional<Address> findById(Long id);

    Optional<UserProfile> findByProfileId(Long profileId);
}

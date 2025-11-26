package com.example.demo.auth.repository;

import com.example.demo.auth.model.User;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD and query operations
 * on {@link User} entities.
 *
 * <p>
 * Extends {@link JpaRepository} to provide:
 * <ul>
 *     <li>Basic CRUD methods</li>
 *     <li>Pagination and sorting capabilities</li>
 *     <li>Automatic query derivation based on method names</li>
 * </ul>
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<@NonNull User, @NonNull Long> {

    /**
     * Finds a user by their unique phone number.
     * <p>
     * Spring Data JPA automatically generates the implementation
     * based on the method name.
     * </p>
     *
     * @param phoneNumber the phone number used to look up the user
     * @return an {@link Optional} containing the user if found,
     * or an empty Optional if no user exists with the given phone number
     */
    Optional<User> findByPhoneNumber(String phoneNumber);
}

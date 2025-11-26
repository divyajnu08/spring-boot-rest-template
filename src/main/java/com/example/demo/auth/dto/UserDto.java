package com.example.demo.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Data Transfer Object representing a simplified view of a user.
 * <p>
 * This DTO is used for sending user information to clients while keeping
 * internal entity structures hidden. It typically represents the authenticated
 * user or users in list responses.
 * </p>
 *
 * <p>
 * The DTO includes:
 * <ul>
 *     <li>The user's phone number</li>
 *     <li>The set of roles assigned to the user</li>
 * </ul>
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    /**
     * The user's phone number.
     * <p>
     * This value is required and must not be blank. It should represent a valid,
     * normalized phone number (commonly in E.164 format).
     * </p>
     */
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    /**
     * The list of roles assigned to the user.
     * <p>
     * Examples include:
     * <ul>
     *     <li>"USER"</li>
     *     <li>"ADMIN"</li>
     *     <li>"SUPER_ADMIN"</li>
     * </ul>
     * </p>
     *
     * <p>
     * Defaults to an empty set to avoid null role collections.
     * </p>
     */
    @Builder.Default
    private Set<String> roles = new HashSet<>();
}

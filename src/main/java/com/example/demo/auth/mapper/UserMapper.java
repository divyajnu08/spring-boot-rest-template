package com.example.demo.auth.mapper;

import com.example.demo.auth.dto.UserDto;
import com.example.demo.auth.model.User;
import org.springframework.stereotype.Component;

/**
 * Mapper responsible for converting between {@link User} entity objects and
 * their corresponding {@link UserDto} representations.
 * <p>
 * Using a mapper helps maintain separation between internal entity models and
 * external API-facing data structures, keeping your architecture clean and
 * reducing coupling.
 * </p>
 */
@Component
public class UserMapper {

    /**
     * Converts a {@link User} entity to a {@link UserDto}.
     *
     * @param user the user entity to convert
     * @return the corresponding UserDto with phone number and roles populated
     */
    public UserDto toDTO(User user) {
        return UserDto.builder()
                .roles(user.getRoles())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    /**
     * Converts a {@link UserDto} to a {@link User} entity.
     * <p>
     * This method reconstructs the User entity using only data exposed in the DTO.
     * Any missing fields (e.g., passwords, internal timestamps) must be handled elsewhere.
     * </p>
     *
     * @param userDto the DTO containing user data
     * @return a new User entity based on the DTO fields
     */
    public User toEntity(UserDto userDto) {
        return new User(
                userDto.getPhoneNumber(),
                userDto.getRoles()
        );
    }
}

package com.example.demo.auth.service;

import com.example.demo.auth.dto.UserDto;
import com.example.demo.auth.mapper.UserMapper;
import com.example.demo.auth.model.User;
import com.example.demo.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service responsible for user-related operations such as:
 * <ul>
 *     <li>Retrieving users</li>
 *     <li>Creating users</li>
 *     <li>Mapping between User entities and UserDto objects</li>
 *     <li>Synchronizing OTP-based authentication with user records</li>
 * </ul>
 *
 * <p>This service acts as the domain layer for user management.</p>
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    /**
     * Constructor-based dependency injection.
     *
     * @param userRepository repository for user persistence operations
     * @param mapper         mapper for converting between User and UserDto
     */
    public UserService(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    /**
     * Creates and persists a new user from a {@link UserDto}.
     *
     * @param userDto the user data to create
     * @return the persisted User entity
     */
    private User createUser(UserDto userDto) {
        User user = new User(userDto.getPhoneNumber(), userDto.getRoles());
        return userRepository.save(user);
    }

    /**
     * Retrieves a user by phone number and converts it to {@link UserDto}.
     *
     * @param phoneNumber the phone number to search for
     * @return a UserDto if found, otherwise {@code null}
     */
    public UserDto getUser(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber).orElse(null);
        return (user != null) ? mapper.toDTO(user) : null;
    }

    /**
     * Retrieves a user entity by phone number.
     *
     * @param phoneNumber the phone number to search for
     * @return a User if found, otherwise {@code null}
     */
    public User findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElse(null);
    }

    /**
     * Retrieves a user by phone number, or creates a new one if no user exists.
     * <p>
     * New users are automatically assigned the role <code>ROLE_USER</code>.
     * </p>
     *
     * @param phoneNumber the phone number associated with the user
     * @return the existing or newly created user
     */
    public User findOrCreateUserByPhoneNumber(String phoneNumber) {
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);

        return userOptional.orElseGet(
                () -> createUser(new UserDto(phoneNumber, Set.of("ROLE_USER")))
        );
    }

    /**
     * Retrieves all users in the system and converts them to DTOs.
     *
     * @return a list of {@link UserDto} objects
     */
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}

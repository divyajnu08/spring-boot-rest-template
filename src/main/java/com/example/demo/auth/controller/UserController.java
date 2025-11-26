package com.example.demo.auth.controller;

import com.example.demo.auth.dto.UserDto;
import com.example.demo.auth.service.UserService;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for handling user-related API endpoints.
 * <p>
 * This controller exposes endpoints to retrieve:
 * <ul>
 *     <li>All registered users</li>
 *     <li>A single user based on phone number</li>
 * </ul>
 * </p>
 *
 * <p>
 * Responses return {@link UserDto} objects rather than entity objects,
 * ensuring that internal domain models are not exposed through the API.
 * </p>
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Constructs the {@link UserController} with the required {@link UserService}.
     *
     * @param userService the service responsible for user-related operations
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves all users in the system.
     *
     * @return a {@link ResponseEntity} containing a list of {@link UserDto}
     */
    @GetMapping
    public ResponseEntity<@NonNull List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Retrieves a single user by their phone number.
     * <p>
     * If the user does not exist, the service layer should handle throwing
     * an appropriate exception (e.g., UserNotFoundException),
     * which can be mapped to a 404 response via an exception handler.
     * </p>
     *
     * @param phoneNumber the unique phone number associated with the user
     * @return a {@link ResponseEntity} containing the user details
     */
    @GetMapping("/{phoneNumber}")
    public ResponseEntity<@NonNull UserDto> getUser(@PathVariable("phoneNumber") String phoneNumber) {
        return ResponseEntity.ok(userService.getUser(phoneNumber));
    }
}

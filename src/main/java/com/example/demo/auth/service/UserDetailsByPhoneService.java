package com.example.demo.auth.service;

import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom {@link UserDetailsService} implementation that loads user details
 * using a phone number instead of the traditional username.
 *
 * <p>This service is used by Spring Security during authentication
 * to retrieve user information (roles, authorities, etc.).</p>
 *
 * <p>The authentication flow typically looks like this:</p>
 * <ol>
 *     <li>A JWT token is parsed and the phone number is extracted</li>
 *     <li>Spring Security calls {@link #loadUserByUsername(String)}</li>
 *     <li>The corresponding {@link com.example.demo.auth.model.User} is loaded</li>
 *     <li>Authorities are returned to complete the authentication process</li>
 * </ol>
 */
@Service
public class UserDetailsByPhoneService implements UserDetailsService {

    private final UserService userService;

    /**
     * Constructor injection of {@link UserService}.
     *
     * @param userService service responsible for user lookup and creation
     */
    public UserDetailsByPhoneService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Loads a user using their phone number.
     *
     * @param phoneNumber the phone number used as the identifier
     * @return a {@link UserDetails} object representing the user
     * @throws UsernameNotFoundException if no user exists with the given phone number
     */
    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String phoneNumber)
            throws UsernameNotFoundException {

        return userService.findUserByPhoneNumber(phoneNumber);
    }
}

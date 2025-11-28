package com.example.demo.auth.config;

import com.example.demo.auth.filter.JwtAuthFilter;
import com.example.demo.auth.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Main Spring Security configuration for the application.
 * <p>
 * Responsible for:
 * - Defining which endpoints are protected or public
 * - Enabling stateless (JWT-based) authentication
 * - Registering the JWT authentication filter
 * - Providing a UserDetailsService bean for security’s authentication process
 */
@Configuration
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    /**
     * Configures the security filter chain.
     * <p>
     * - Disables CSRF since tokens make CSRF protection unnecessary
     * - Sets session policy to STATELESS because we rely entirely on JWT
     * - Allows unauthenticated access to /api/auth/** routes
     * - Requires authentication for all other endpoints
     * - Registers the JwtAuthFilter to run before Spring Security’s username/password filter
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * UserDetailsService bean used by Spring Security during authentication.
     * <p>
     * - Instead of creating a separate UserDetailsService class,
     * we expose it as a bean to keep the design simple.
     * <p>
     * - Spring Security will call this method to load user details (by phone number)
     * whenever authentication is needed.
     * <p>
     * - We delegate to our existing UserService, avoiding duplicate logic.
     */
    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return userService::findUserByPhoneNumber;
    }
}

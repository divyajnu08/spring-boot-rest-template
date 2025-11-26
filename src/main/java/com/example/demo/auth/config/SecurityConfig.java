package com.example.demo.auth.config;

import com.example.demo.auth.filter.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration for the application.
 * <p>
 * This class defines how HTTP security should behave, including:
 * <ul>
 *     <li>Stateless session management</li>
 *     <li>Disabling CSRF for token-based authentication</li>
 *     <li>Route-level authorization rules</li>
 *     <li>Placement of a custom JWT authentication filter</li>
 * </ul>
 * </p>
 *
 * <p>
 * The configuration ensures that:
 * <ul>
 *     <li>All authentication-related APIs under <code>/api/auth/**</code> are publicly accessible</li>
 *     <li>All other endpoints require authentication</li>
 *     <li>A {@link JwtAuthFilter} is executed before the default
 *         {@link UsernamePasswordAuthenticationFilter}</li>
 * </ul>
 * </p>
 */
@Configuration
public class SecurityConfig {

    /**
     * Defines the main security filter chain for the application.
     * <p>
     * This method configures Spring Security with:
     * <ul>
     *     <li>Disabled CSRF (suitable for token-based stateless authentication)</li>
     *     <li>Stateless session policy since JWT will be used</li>
     *     <li>Public access to authentication endpoints</li>
     *     <li>A custom JWT filter added before Spring Security's authentication filter</li>
     * </ul>
     * </p>
     *
     * @param http          the {@link HttpSecurity} builder for configuring web security
     * @param jwtAuthFilter the custom JWT authentication filter responsible for validating tokens
     * @return the built {@link SecurityFilterChain}
     * @throws Exception if an error occurs during configuration
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
}

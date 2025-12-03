package com.example.demo.auth.model;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Entity representing an application user authenticated primarily using
 * their phone number (rather than username/password).
 *
 * <p>This class also implements {@link UserDetails} so it can integrate
 * with Spring Security’s authentication framework.</p>
 *
 * <p>Users contain:</p>
 * <ul>
 *     <li>A unique database ID</li>
 *     <li>A unique phone number (used as the username)</li>
 *     <li>A set of assigned roles</li>
 * </ul>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    /**
     * Primary key for the user entity.
     * Automatically generated using identity strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique phone number used as the authentication identifier.
     * <p>Must not be null and must be unique in the database.</p>
     */
    @NonNull
    @Column(unique = true, nullable = false)
    private String phoneNumber;

    /**
     * Roles assigned to the user.
     * <p>
     * Stored as a separate collection table (<code>user_roles</code>) and
     * eagerly loaded since they are required for every authentication process.
     * </p>
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserProfile userProfile;

    /**
     * Convenience constructor for manual user creation.
     *
     * @param phoneNumber the user's phone number (required)
     * @param roles       the set of user roles
     */
    public User(@NonNull String phoneNumber,
                Set<String> roles) {
        this.phoneNumber = phoneNumber;
        this.setRoles(roles);
    }

    /**
     * Converts user roles to {@link GrantedAuthority} instances for Spring Security.
     *
     * @return a collection of granted authorities derived from user roles
     */
    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }

    /**
     * Users authenticated via OTP have no password.
     *
     * @return always {@code null}
     */
    @Override
    public @Nullable String getPassword() {
        return null;
    }

    /**
     * The phone number is used as the username for Spring Security.
     *
     * @return the phone number of the user
     */
    @Override
    public @NonNull String getUsername() {
        return phoneNumber;
    }

    // Spring Security default fields (non-expiring, non-locked, always enabled)

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

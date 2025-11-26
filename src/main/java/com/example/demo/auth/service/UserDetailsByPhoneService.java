package com.example.demo.auth.service;

import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsByPhoneService implements UserDetailsService {

    private final UserService userService;

    public UserDetailsByPhoneService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String phoneNumber) throws UsernameNotFoundException {
        return userService.findUserByPhoneNumber(phoneNumber);
    }
}

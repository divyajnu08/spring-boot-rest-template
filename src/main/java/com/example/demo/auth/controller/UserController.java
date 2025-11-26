package com.example.demo.auth.controller;

import com.example.demo.auth.dto.UserDto;
import com.example.demo.auth.model.User;
import com.example.demo.auth.service.UserService;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<@NonNull List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<@NonNull UserDto> getUser(@PathVariable("phoneNumber") String phoneNumber) {
        return ResponseEntity.ok(userService.getUser(phoneNumber));
    }
}

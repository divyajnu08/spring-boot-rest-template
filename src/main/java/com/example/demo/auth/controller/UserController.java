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

    @PostMapping
    public ResponseEntity<@NonNull User> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @GetMapping
    public ResponseEntity<@NonNull List<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}

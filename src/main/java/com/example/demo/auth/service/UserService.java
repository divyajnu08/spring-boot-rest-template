package com.example.demo.auth.service;

import com.example.demo.auth.dto.UserDto;
import com.example.demo.auth.model.User;
import com.example.demo.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserDto userDto) {
        User user = new User(userDto.getPhoneNumber(), userDto.getRole());
        return userRepository.save(user);
    }

    public User findUserByName(String phoneNumber) {
        List<User> allUsers = userRepository.findAll();
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        if (user.isEmpty()) {
            System.out.println("Optional user is empty , so creating new User");
            return createUser(new UserDto(phoneNumber, "ROLE_USER"));
        }
        return user.get();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

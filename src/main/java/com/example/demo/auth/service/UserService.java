package com.example.demo.auth.service;

import com.example.demo.auth.dto.UserDto;
import com.example.demo.auth.model.User;
import com.example.demo.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return userRepository.findByPhoneNumber(phoneNumber).orElse(createUser(
                new UserDto(phoneNumber, "ROLE_USER")
        ));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

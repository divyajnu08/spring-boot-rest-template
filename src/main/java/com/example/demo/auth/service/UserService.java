package com.example.demo.auth.service;

import com.example.demo.auth.dto.UserDto;
import com.example.demo.auth.mapper.UserMapper;
import com.example.demo.auth.model.User;
import com.example.demo.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserService(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    private User createUser(UserDto userDto) {
        User user = new User(userDto.getPhoneNumber(), userDto.getRoles());
        return userRepository.save(user);
    }

    public UserDto getUser(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber).orElse(null);
        if (user != null) {
            return mapper.toDTO(user);
        }
        return null;
    }

    public User findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElse(null);
    }

    public User findOrCreateUserByPhoneNumber(String phoneNumber) {
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        return userOptional.orElseGet(() -> createUser(new UserDto(phoneNumber, Set.of("ROLE_USER"))));

    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}

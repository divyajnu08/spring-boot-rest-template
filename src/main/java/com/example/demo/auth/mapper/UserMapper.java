package com.example.demo.auth.mapper;

import com.example.demo.auth.dto.UserDto;
import com.example.demo.auth.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDTO(User user) {
        return UserDto.builder()
                .roles(user.getRoles())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public User toEntity(UserDto userDto) {
        return new User(
                userDto.getPhoneNumber(),
                userDto.getRoles()
        );
    }
}

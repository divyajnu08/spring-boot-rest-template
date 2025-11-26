package com.example.demo.auth.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    @NotBlank(message = "Name is required")
    private String phoneNumber;

    @Builder.Default
    private Set<String> roles = new HashSet<>();
}

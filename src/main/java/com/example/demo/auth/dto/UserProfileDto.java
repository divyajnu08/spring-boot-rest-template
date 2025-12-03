package com.example.demo.auth.dto;

import com.example.demo.auth.model.Address;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class UserProfileDto {

    private String firstName;
    private String lastName;
    private String email;
    private List<AddressDto> userAddresses;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDate dateOfBirth;

}

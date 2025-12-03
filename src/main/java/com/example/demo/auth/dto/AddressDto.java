package com.example.demo.auth.dto;

import com.example.demo.auth.model.UserProfile;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.data.geo.Point;

public class AddressDto {

    private String line1;
    private String line2;
    private String placeId;
    private Point location;
    private Boolean defaultAddress;
    private String placeName;
    private UserProfileDto userProfile;

}

package com.example.demo.auth.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Point;

@Getter
@Setter
@Entity(name = "addresses")
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String line1;
    private String line2;
    private String placeId;
    private Point location;
    private Boolean defaultAddress;
    private String placeName;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    @JsonBackReference
    private UserProfile userProfile;

}

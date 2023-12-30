package com.redeyesncode.estatespring.realestatebackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InsightProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_listing_id")
    private UserListing userListing;

    private int messageCount;

    private int phoneViewCount;

    private int impressionCount;

    // Constructors, getters, setters, etc.
}

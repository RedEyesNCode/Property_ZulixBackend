package com.redeyesncode.estatespring.realestatebackend.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddonPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String packageName;
    private String packageMessage;
    private double price;

    @ManyToOne
    @JoinColumn(name = "user_listing_id")
    private UserListing userListing;
    // Constructors, getters, and setters go here

    // Don't forget your constructors, getters, setters, or any other methods you need for this entity!
}


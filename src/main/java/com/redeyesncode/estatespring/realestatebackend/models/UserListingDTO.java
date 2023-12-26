package com.redeyesncode.estatespring.realestatebackend.models;


import lombok.Data;

@Data
public class UserListingDTO {
    private Long userId;
    private ListingType listingType;
    private String addressLine1;
    private String addressLine2;
    private String town;
    private String city;
    private String postalCode;

    // Constructors, getters, setters, etc.
}


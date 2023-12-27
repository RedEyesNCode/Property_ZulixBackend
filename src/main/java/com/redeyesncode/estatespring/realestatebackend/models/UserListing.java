package com.redeyesncode.estatespring.realestatebackend.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserListing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserTable user;

    @Enumerated(EnumType.STRING)
    private ListingType listingType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    private PropertyDetails property;

    @Enumerated(EnumType.STRING)
    private ListingStatus listingStatus = ListingStatus.REVIEWING;

    private String deniedReason = "";
    // Constructors, getters, setters, etc.
    public enum ListingStatus {
        ACTIVE,
        INACTIVE,
        REVIEWING,
        DENIED,
        EMPTY // Optional: For default or initial value handling
    }

}


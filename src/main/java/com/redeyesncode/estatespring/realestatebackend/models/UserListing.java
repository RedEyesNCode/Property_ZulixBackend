package com.redeyesncode.estatespring.realestatebackend.models;

import com.redeyesncode.estatespring.realestatebackend.models.common.ListingType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToOne(cascade = CascadeType.REMOVE)
    private PropertyDetails property;

    @Enumerated(EnumType.STRING)
    private ListingStatus listingStatus = ListingStatus.REVIEWING;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userListing")
    private List<AddonPackage> addonPackages = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Subscription subscription;

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


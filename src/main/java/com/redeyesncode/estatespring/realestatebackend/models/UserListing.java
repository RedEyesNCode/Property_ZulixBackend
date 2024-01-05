package com.redeyesncode.estatespring.realestatebackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redeyesncode.estatespring.realestatebackend.models.common.ListingType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<AddonPackage> addonPackages;

    @OneToOne(mappedBy = "userListing", cascade = CascadeType.ALL)
    private PaymentRecord paymentRecord;


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


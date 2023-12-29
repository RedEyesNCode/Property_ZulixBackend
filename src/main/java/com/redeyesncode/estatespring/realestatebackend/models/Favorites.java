package com.redeyesncode.estatespring.realestatebackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "favorites")
@Getter
@Setter
public class Favorites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserTable user;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "listing_id")
    private UserListing listing;

    @Enumerated(EnumType.STRING)
    private Status status;
    public enum Status {
        ADDED,   // Represents that the listing is added to favorites
        REMOVED  // Represents that the listing is removed from favorites
    }
}

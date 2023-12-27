package com.redeyesncode.estatespring.realestatebackend.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tourDateTime;

    private String requesterName;

    private String requesterPhoneNumber;

    @Enumerated(EnumType.STRING)
    private TourRequestStatus status = TourRequestStatus.EMPTY;

    @ManyToOne
    @JoinColumn(name = "user_listing_id")
    private UserListing userListing;

    @ManyToOne
    @JoinColumn(name = "requester_user_id")
    private UserTable requester;


    public enum TourRequestStatus {
        EMPTY,
        ACCEPT,
        DENIED
    }

}

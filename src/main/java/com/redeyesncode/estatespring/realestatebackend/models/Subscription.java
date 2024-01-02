package com.redeyesncode.estatespring.realestatebackend.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection
    private List<String> featuresList;

    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private SubscriptionType type;

    // Constructors, getters, setters, etc.

    public enum SubscriptionType {
        MONTHLY,
        YEARLY
    }
}

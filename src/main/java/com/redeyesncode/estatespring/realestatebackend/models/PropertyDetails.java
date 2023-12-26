package com.redeyesncode.estatespring.realestatebackend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class PropertyDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    private String furnishingOptions;

    private double price;

    private int numberOfBathrooms;

    private int numberOfKitchens;

    private int numberOfBedrooms;

    private int propertyAge;

    private double carpetArea;

    @ElementCollection
    private List<String> keyFeatures = new ArrayList<>();

    private String photo1;

    private String photo2;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate tourDate;

    private String phoneNumber;

    private boolean showPropertyPhone;
    public enum Status {
        AGENT,
        BUYER,
        REVIEWER
    }
    public enum PropertyType {
        HOUSE,
        APARTMENT,
        CONDO,
        TOWNHOUSE,
        LAND,
        COMMERCIAL,
        OTHER
    }

}

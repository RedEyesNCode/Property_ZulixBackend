package com.redeyesncode.estatespring.realestatebackend.models;

import lombok.Data;

@Data
public class ListingSearchCriteriaDTO {
    private ListingType listingType;
    private String postalCode;
    private String city;
    private String town;
    private Integer numberOfBedrooms;
    private Integer numberOfBathrooms;
    private Integer numberOfKitchens;
    private Double minPrice;
    private Double maxPrice;
}

package com.redeyesncode.estatespring.realestatebackend.models;

import lombok.Data;

@Data
public class AddPropertyDetailsDTO {
    private Long userListingId;
    private PropertyDetails propertyDetails;

}

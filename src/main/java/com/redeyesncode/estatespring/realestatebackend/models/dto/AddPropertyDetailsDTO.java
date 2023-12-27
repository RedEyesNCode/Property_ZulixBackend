package com.redeyesncode.estatespring.realestatebackend.models.dto;

import com.redeyesncode.estatespring.realestatebackend.models.PropertyDetails;
import lombok.Data;

@Data
public class AddPropertyDetailsDTO {
    private Long userListingId;
    private PropertyDetails propertyDetails;

}

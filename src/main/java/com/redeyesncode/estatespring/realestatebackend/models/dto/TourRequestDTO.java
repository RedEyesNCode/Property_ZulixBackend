package com.redeyesncode.estatespring.realestatebackend.models.dto;

import lombok.Data;

@Data
public class TourRequestDTO {
    private String listingId;
    private String requesterId;
    private String tourDateTime;
    private String requesterName;
    private String requesterPhoneNumber;
    private String status;


}

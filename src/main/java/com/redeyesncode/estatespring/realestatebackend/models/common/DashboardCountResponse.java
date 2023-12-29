package com.redeyesncode.estatespring.realestatebackend.models.common;


import lombok.Data;

@Data
public class DashboardCountResponse {
    private String messageCount;
    private String notificationCount;
    private String myProperties;
    private String savedListing;

}

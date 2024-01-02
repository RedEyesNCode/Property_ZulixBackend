package com.redeyesncode.estatespring.realestatebackend.models.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {
    private String name;
    private List<String> featuresList;
    private double totalPrice;
    private String type;

}

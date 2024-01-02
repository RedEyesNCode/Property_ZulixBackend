package com.redeyesncode.estatespring.realestatebackend.models.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddonDto {

    private String packageName;
    private String packageMessage;
    private double price;

}

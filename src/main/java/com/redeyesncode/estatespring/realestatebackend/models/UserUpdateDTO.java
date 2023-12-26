package com.redeyesncode.estatespring.realestatebackend.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateDTO {
    private int userId;
    private String fullName;
    private String email;
    private String telephoneNumber;
    private String postcode;
    private String password;
    private String userName;


    // Constructor can be added if needed, Lombok's @NoArgsConstructor is used here
}
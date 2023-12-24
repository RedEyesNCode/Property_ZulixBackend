package com.redeyesncode.estatespring.realestatebackend.models;

import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UserRegistrationDTO {
    private String password;
    private String email;
    private String telephoneNumber;
}

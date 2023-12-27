package com.redeyesncode.estatespring.realestatebackend.models.common;


import com.redeyesncode.estatespring.realestatebackend.models.UserTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginJwtResponse {

    private UserTable user;
    private String JWT;
    private String message;
    private int code;
    private String status;


}

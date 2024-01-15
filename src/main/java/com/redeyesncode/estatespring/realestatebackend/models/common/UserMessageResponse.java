package com.redeyesncode.estatespring.realestatebackend.models.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMessageResponse {

    private String status;
    private int code;
    private String message;

    private ArrayList<UserListingMessageModel> data;

}

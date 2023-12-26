package com.redeyesncode.estatespring.realestatebackend.controllers;


import com.redeyesncode.estatespring.realestatebackend.models.*;
import com.redeyesncode.estatespring.realestatebackend.service.ListingService;
import com.redeyesncode.estatespring.realestatebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/spring-property")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ListingService listingService;


    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO registerUserDTO){

        return userService.registerUser(registerUserDTO);

    }

    @PostMapping("/login-user")
    public ResponseEntity<?> loginUser(@RequestBody HashMap<String,String> loginMap){


        return userService.loginUser(loginMap);
    }

    @PostMapping("/check-username")
    public ResponseEntity<?> checkUserName(@RequestBody HashMap<String,String> hashMap){

        return userService.checkUsername(hashMap);

    }
    @PostMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody UserUpdateDTO userUpdateDTO){

        return userService.updateUserProfile(userUpdateDTO);

    }
    @PostMapping("/update-listing")
    public ResponseEntity<?> updateListing(@RequestBody UserUpdateListingDTO userUpdateListingDTO){

        return listingService.updateUserListing(userUpdateListingDTO);

    }
    @PostMapping("/add-listing")
    public ResponseEntity<?> addListing(@RequestBody UserListingDTO userUpdateListingDTO){

        return listingService.addUserListing(userUpdateListingDTO);

    }
    @PostMapping("/get-listing")
    public ResponseEntity<?> getListing(@RequestBody HashMap<String,String> userIdMap){

        return listingService.getAllListingsByUserId(Long.valueOf(userIdMap.get("userId")));

    }

    @PostMapping("/add-listing-property")
    public ResponseEntity<?> addListingProperty(@RequestBody AddPropertyDetailsDTO detailsDTO){

        return listingService.addPropertyDetailsToUserListing(detailsDTO.getUserListingId(), detailsDTO.getPropertyDetails());
    }

}

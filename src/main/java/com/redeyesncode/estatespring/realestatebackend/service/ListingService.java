package com.redeyesncode.estatespring.realestatebackend.service;

import com.redeyesncode.estatespring.realestatebackend.models.*;
import com.redeyesncode.estatespring.realestatebackend.repository.PropertyDetailsRepo;
import com.redeyesncode.estatespring.realestatebackend.repository.UserListingRepo;
import com.redeyesncode.estatespring.realestatebackend.repository.UserTableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListingService {

    @Autowired
    private UserListingRepo userListingRepo;

    @Autowired
    private UserTableRepo userTableRepo;

    @Autowired
    private PropertyDetailsRepo propertyDetailsRepo;


    public ResponseEntity<?> addPropertyDetailsToUserListing(Long userListingId, PropertyDetails propertyDetails) {
        // Check if the user listing exists based on userListingId
        Optional<UserListing> optionalUserListing = userListingRepo.findById(userListingId);
        if (optionalUserListing.isPresent()) {
            UserListing userListing = optionalUserListing.get();

            // Associate PropertyDetails with UserListing
            userListing.setProperty(propertyDetails);

            // Save PropertyDetails to the repository
            try {
                PropertyDetails savedPropertyDetails = propertyDetailsRepo.save(propertyDetails);
                return ResponseEntity.ok(new CustomStatusCodeModel("200", 200, "Property details added to user listing", savedPropertyDetails));
            } catch (Exception e) {
                return BadResponseMessage("Failed to add property details");
            }
        } else {
            return BadResponseMessage("User listing not found");
        }
    }


    @Autowired
    private AddressRepo addressRepo;
    private static final ResponseEntity<?> SUCCESS_RESPONSE = ResponseEntity.ok(new StatusCodeModel("200",200,"Success"));
    private static final ResponseEntity<?> BAD_RESPONSE = ResponseEntity.badRequest().body(new StatusCodeModel("400",400,"Fail"));


    public static ResponseEntity<?> BadResponseMessage(String message){
        return ResponseEntity.badRequest().body(new StatusCodeModel("400",400,message));

    }

    public static ResponseEntity<?> SuccessResponseMessage(String message){
        return ResponseEntity.ok(new StatusCodeModel("200",200,message));

    }

    public ResponseEntity<?> getAllListingsByUserId(Long userId) {
        if(userListingRepo.findAllByUserId(userId).isEmpty()){
            return BadResponseMessage("No Listings Found !");
        }else{
            return ResponseEntity.ok(new CustomStatusCodeModel("success",200,"User-Listings",userListingRepo.findAllByUserId(userId)));

        }



    }
    public ResponseEntity<?> addUserListing(UserListingDTO newListingDTO) {
        Long userId = newListingDTO.getUserId();
        ListingType listingType = newListingDTO.getListingType();
        String addressLine1 = newListingDTO.getAddressLine1();
        String addressLine2 = newListingDTO.getAddressLine2();
        String town = newListingDTO.getTown();
        String city = newListingDTO.getCity();
        String postalCode = newListingDTO.getPostalCode();

        // Perform validations or business logic here if needed

        // Find user based on userId
        Optional<UserTable> optionalUser = userTableRepo.findById(userId);
        if (optionalUser.isPresent()) {
            UserTable user = optionalUser.get();

            // Create Address entity
            Address address = new Address();
            address.setAddressLine1(addressLine1);
            address.setAddressLine2(addressLine2);
            address.setTown(town);
            address.setCity(city);
            address.setPostalCode(postalCode);

            // Create UserListing entity
            UserListing newUserListing = new UserListing();
            newUserListing.setUser(user);
            newUserListing.setListingType(listingType);
            newUserListing.setAddress(address);

            // Save the new user listing to the database
            try {
                UserListing createdUserListing = userListingRepo.save(newUserListing);
                return ResponseEntity.ok(new CustomStatusCodeModel("200", 200, "New user listing created", createdUserListing));
            } catch (Exception e) {
                return BadResponseMessage("Failed to create new user listing");
            }
        } else {
            return BadResponseMessage("User not found");
        }
    }


    public ResponseEntity<?> updateUserListing(UserUpdateListingDTO updateListingDTO) {
        Long listingId = updateListingDTO.getListingId(); // Assuming DTO contains listingId for identification
        ListingType listingType = updateListingDTO.getListingType();
        // Assuming the DTO contains fields related to address
        String addressLine1 = updateListingDTO.getAddressLine1();
        String addressLine2 = updateListingDTO.getAddressLine2();
        String town = updateListingDTO.getTown();
        String city = updateListingDTO.getCity();
        String postalCode = updateListingDTO.getPostalCode();

        // Perform validations or business logic here if needed

        // Check if the user listing exists based on listingId
        Optional<UserListing> optionalUserListing = userListingRepo.findById(listingId);
        if (optionalUserListing.isPresent()) {
            UserListing existingUserListing = optionalUserListing.get();

            // Update user listing details
            existingUserListing.setListingType(listingType);

            // Update address details
            Address address = existingUserListing.getAddress();
            address.setAddressLine1(addressLine1);
            address.setAddressLine2(addressLine2);
            address.setTown(town);
            address.setCity(city);
            address.setPostalCode(postalCode);

            // Update timestamps (if needed) for the listing or address

            // Save the updated user listing to the database
            try {
                UserListing updatedUserListing = userListingRepo.save(existingUserListing);
                return ResponseEntity.ok(new CustomStatusCodeModel("200", 200, "User listing updated", updatedUserListing));
            } catch (Exception e) {
                return BadResponseMessage("Failed to update user listing");
            }
        } else {
            return BadResponseMessage("User listing not found");
        }
    }
}

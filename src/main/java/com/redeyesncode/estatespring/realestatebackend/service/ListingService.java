package com.redeyesncode.estatespring.realestatebackend.service;

import com.redeyesncode.estatespring.realestatebackend.models.*;
import com.redeyesncode.estatespring.realestatebackend.models.common.CustomStatusCodeModel;
import com.redeyesncode.estatespring.realestatebackend.models.common.ListingType;
import com.redeyesncode.estatespring.realestatebackend.models.common.StatusCodeModel;
import com.redeyesncode.estatespring.realestatebackend.models.dto.TourRequestDTO;
import com.redeyesncode.estatespring.realestatebackend.models.dto.UserListingDTO;
import com.redeyesncode.estatespring.realestatebackend.models.dto.UserUpdateListingDTO;
import com.redeyesncode.estatespring.realestatebackend.repository.*;
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

    @Autowired
    private TourRequestRepository tourRequestRepository;

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
    public ResponseEntity<?> getListingsByStatus(String listingStatus,String userId) {
        try {
            // Fetch listings based on the provided status string
            UserListing.ListingStatus status = UserListing.ListingStatus.valueOf(listingStatus);
            List<UserListing> listings = userListingRepo.findByListingStatusAndUserId(status, Long.valueOf(userId));
            if(listings.isEmpty()){
                return BadResponseMessage("No Listings Found !");
            }else{
                return ResponseEntity.ok(new CustomStatusCodeModel("200", 200, "Listings fetched successfully", listings));

            }

            // Return the listings in the desired format
        } catch (Exception e) {
            return BadResponseMessage(e.getMessage());
        }
    }

    public ResponseEntity<?> updateListingStatusByListingId(String listingStatus, String listingId) {
        try {
            UserListing.ListingStatus status = UserListing.ListingStatus.valueOf(listingStatus);
            Long id = Long.valueOf(listingId);

            UserListing listing = userListingRepo.findById(id).orElse(null);
            if (listing == null) {
                return BadResponseMessage("Listing with ID " + id + " not found !");
            }

            // Update the listing status
            listing.setListingStatus(status);

            // Save the updated listing
            UserListing updatedListing = userListingRepo.save(listing);

            return ResponseEntity.ok(new CustomStatusCodeModel("200", 200, "Listing updated successfully", updatedListing));
        } catch (IllegalArgumentException e) {
            return BadResponseMessage("Invalid listing status provided: " + listingStatus);
        } catch (Exception e) {
            return BadResponseMessage(e.getMessage());
        }
    }
    public ResponseEntity<?> deleteListingByListingId(String listingId) {
        try {
            Long id = Long.valueOf(listingId);

            UserListing listing = userListingRepo.findById(id).orElse(null);
            if (listing == null) {
                return BadResponseMessage("Listing with ID " + id + " not found !");
            }

            // Delete the listing
            userListingRepo.delete(listing);

            return ResponseEntity.ok(new CustomStatusCodeModel("200", 200, "Listing deleted successfully", null));
        } catch (Exception e) {
            return BadResponseMessage(e.getMessage());
        }
    }
    public ResponseEntity<?> fetchTourRequestsForUser(String userId) {
        try {
            Long id = Long.valueOf(userId);
            List<TourRequest> tourRequests = tourRequestRepository.findTourRequestsForUser(id);

            if (tourRequests.isEmpty()) {
                return BadResponseMessage("No tour requests found for user with ID " + id);
            }

            return ResponseEntity.ok(new CustomStatusCodeModel("200", 200, "Tour requests fetched successfully", tourRequests));
        } catch (NumberFormatException e) {
            return BadResponseMessage("Invalid user ID format: " + userId);
        } catch (Exception e) {
            return BadResponseMessage(e.getMessage());
        }
    }

    public ResponseEntity<?> updateTourRequestStatus(String tourRequestId, String status) {
        try {
            Long id = Long.valueOf(tourRequestId);
            TourRequest.TourRequestStatus tourRequestStatus = TourRequest.TourRequestStatus.valueOf(status);

            TourRequest tourRequest = tourRequestRepository.findById(id).orElse(null);
            if (tourRequest == null) {
                return BadResponseMessage("Tour request with ID " + id + " not found !");
            }

            tourRequest.setStatus(tourRequestStatus);
            TourRequest updatedTourRequest = tourRequestRepository.save(tourRequest);

            return ResponseEntity.ok(new CustomStatusCodeModel("200", 200, "Tour request status updated successfully", updatedTourRequest));
        } catch (NumberFormatException e) {
            return BadResponseMessage("Invalid tour request ID format: " + tourRequestId);
        } catch (IllegalArgumentException e) {
            return BadResponseMessage("Invalid status provided: " + status);
        } catch (Exception e) {
            return BadResponseMessage(e.getMessage());
        }
    }

    public ResponseEntity<?> createTourRequest(TourRequestDTO tourRequestDTO) {
        try {
            Long userListingId = Long.valueOf(tourRequestDTO.getListingId());
            Long userId = Long.valueOf(tourRequestDTO.getRequesterId());

            UserListing userListing = userListingRepo.findById(userListingId).orElse(null);
            UserTable requester = userTableRepo.findById(userId).orElse(null);

            if (userListing == null) {
                return BadResponseMessage("User listing with ID " + userListingId + " not found !");
            }

            if (requester == null) {
                return BadResponseMessage("Requester with ID " + userId + " not found !");
            }

            TourRequest tourRequest = new TourRequest();
            tourRequest.setTourDateTime(tourRequestDTO.getTourDateTime());
            tourRequest.setRequesterName(tourRequestDTO.getRequesterName());
            tourRequest.setRequesterPhoneNumber(tourRequestDTO.getRequesterPhoneNumber());
            tourRequest.setUserListing(userListing);
            tourRequest.setRequester(requester);
            tourRequest.setStatus(TourRequest.TourRequestStatus.EMPTY);

            TourRequest createdTourRequest = tourRequestRepository.save(tourRequest);

            return ResponseEntity.ok(new CustomStatusCodeModel("200", 200, "Tour request created successfully", createdTourRequest));
        } catch (Exception e) {
            return BadResponseMessage(e.getMessage());
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
            newUserListing.setListingStatus(UserListing.ListingStatus.REVIEWING);
            newUserListing.setDeniedReason("");

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

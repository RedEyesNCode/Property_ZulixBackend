package com.redeyesncode.estatespring.realestatebackend.service;

import com.redeyesncode.estatespring.realestatebackend.models.*;
import com.redeyesncode.estatespring.realestatebackend.models.common.*;
import com.redeyesncode.estatespring.realestatebackend.models.dto.*;
import com.redeyesncode.estatespring.realestatebackend.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PaymentRecordRepo paymentRecordRepository;


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
    @Autowired
    private AddOnRepository addOnRepository;


    @Transactional
    public ResponseEntity<?> addAddonPackageForUserListing(Long userListingId, Long packageId) {
        try {
            Optional<UserListing> userListingOptional = userListingRepo.findById(userListingId);
            if (userListingOptional.isPresent()) {
                UserListing userListing = userListingOptional.get();

                if (addOnRepository.existsById(packageId)) {
                    AddonPackage addonPackage = addOnRepository.getById(packageId);

                    // Ensure the addonPackage is not already associated with the UserListing
                    if (!userListing.getAddonPackages().contains(addonPackage)) {
                        List<AddonPackage> addonPackages = userListing.getAddonPackages();
                        addonPackages.add(addonPackage);
                        userListing.setAddonPackages(addonPackages);

                        userListingRepo.saveAndFlush(userListing);
                        return ResponseEntity.ok(new CustomStatusCodeModel("200", 200, "AddonPackage added to UserListing successfully!", userListing));
                    } else {
                        return BadResponseMessage("AddonPackage is already associated with the UserListing!");
                    }
                } else {
                    return BadResponseMessage("AddonPackage not found!");
                }
            } else {
                return BadResponseMessage("UserListing Not Found!");
            }
        } catch (Exception e) {
            return BadResponseMessage(e.getMessage());
        }
    }

    public ResponseEntity<?> getListingBill(String listingId){
        try {
            Optional<UserListing> userListingOptional = userListingRepo.findById(Long.valueOf(listingId));
            if (userListingOptional.isPresent()) {
                ResponseListingBill responseListingBill = new ResponseListingBill();
                responseListingBill.setSubscription(userListingOptional.get().getSubscription());
                responseListingBill.setAddons(userListingOptional.get().getAddonPackages());
                responseListingBill.setTotalBill(userListingRepo.calculateTotalPriceForUserListing(Long.valueOf(listingId)));
                return ResponseEntity.ok(new CustomStatusCodeModel("200",200,"Total-Listing Bill",responseListingBill));

            } else {
               return BadResponseMessage("Listing Not Found");
            }
        }catch (Exception e){
            e.printStackTrace();
            return BadResponseMessage(e.getMessage());

        }

    }

    public ResponseEntity<?> removeAddonPackageFromUserListing(Long userListingId, Long packageId) {
        try {
            Optional<UserListing> userListingOptional = userListingRepo.findById(userListingId);
            if (userListingOptional.isPresent()) {
                UserListing userListing = userListingOptional.get();

                if (addOnRepository.existsById(packageId)) {
                    AddonPackage addonPackage = addOnRepository.getById(packageId);

                    if (userListing.getAddonPackages().contains(addonPackage)) {
                        List<AddonPackage> addonPackages = userListing.getAddonPackages();
                        addonPackages.remove(addonPackage);
                        userListing.setAddonPackages(addonPackages);

                        userListingRepo.saveAndFlush(userListing);
                        return ResponseEntity.ok(new CustomStatusCodeModel("200", 200, "AddonPackage removed from UserListing successfully!", userListing));
                    } else {
                        return BadResponseMessage("AddonPackage is not associated with the UserListing!");
                    }
                } else {
                    return BadResponseMessage("AddonPackage not found!");
                }
            } else {
                return BadResponseMessage("UserListing Not Found!");
            }
        } catch (Exception e) {
            return BadResponseMessage(e.getMessage());
        }
    }

    public ResponseEntity<?> updateListingStatusByListingId(String listingStatus, String listingId) {
        try {
            UserListing.ListingStatus status;

            if(listingStatus.equals("INACTIVE")){
                 status = UserListing.ListingStatus.INACTIVE;
            }else if(listingStatus.equals("ACTIVE")){
                 status = UserListing.ListingStatus.ACTIVE;
            }else if(listingStatus.equals("REVIEWING")){
                status = UserListing.ListingStatus.REVIEWING;

            }else if (listingStatus.equals("DENIED")){
                status = UserListing.ListingStatus.DENIED;

            }else {
               status = UserListing.ListingStatus.EMPTY;

            }

            Long id = Long.valueOf(listingId);

            UserListing listing = userListingRepo.findById(id).orElse(null);
            if (listing == null) {
                return BadResponseMessage("Listing with ID " + id + " not found !");
            }

            // Update the listing status
            listing.setListingStatus(status);

            // Save the updated listing
            UserListing updatedListing = userListingRepo.save(listing);

            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setMessage("Your Listing Status has updated !");
            notificationDTO.setTitle("Listing Status Update !");
            notificationDTO.setUserListingId(String.valueOf(updatedListing.getId()));
            notificationDTO.setSenderId("-1");
            notificationDTO.setReceiverId(String.valueOf(updatedListing.getUser().getUserId()));
            notificationDTO.setNotificationType(String.valueOf(NotificationType.VIEWED));

            notificationService.insertNotification(notificationDTO);


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

            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setMessage("You have received a Tour Request");
            notificationDTO.setTitle("Listing Tour !");
            notificationDTO.setUserListingId(String.valueOf(userListingId));
            notificationDTO.setSenderId(String.valueOf(userId));
            notificationDTO.setReceiverId(String.valueOf(userListingRepo.getById(Long.valueOf(userListingId)).getUser().getUserId()));
            notificationDTO.setNotificationType(String.valueOf(NotificationType.VIEWED));

            notificationService.insertNotification(notificationDTO);


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

    public void createPaymentRecord(String listingId, String paymentIntentId) {
        // Assuming PaymentRecord entity has setters for listingId and paymentIntentId
        PaymentRecord paymentRecord = new PaymentRecord();
        Optional<UserListing> userListingOptional = userListingRepo.findById(Long.valueOf(listingId));

        if(userListingOptional.isPresent()){
            if(!paymentRecordRepository.existsByUserListing(userListingOptional.get())){
                paymentRecord.setPaymentMethod("card");
                paymentRecord.setPaymentDate(LocalDateTime.now());
                paymentRecord.setPaymentStatus("success");
                paymentRecord.setUserListing(userListingOptional.get());
                paymentRecord.setPaymentIntentId(paymentIntentId);
                paymentRecordRepository.save(paymentRecord);

            }

        }

        // Save the PaymentRecord entity
    }
}

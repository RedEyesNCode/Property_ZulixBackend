package com.redeyesncode.estatespring.realestatebackend.controllers;


import com.redeyesncode.estatespring.realestatebackend.aws.StorageService;
import com.redeyesncode.estatespring.realestatebackend.models.dto.*;
import com.redeyesncode.estatespring.realestatebackend.service.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
@RequestMapping("/spring-property")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ListingService listingService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private FavoritesService favoritesService;


    @Autowired
    private InsightPropertyService insightPropertyService;




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

    @PostMapping("/upload-file")
    public ResponseEntity<?> uploadFile(@RequestParam("image_file") MultipartFile file){

        return storageService.uploadFile(file);


    }

    @PostMapping("/search-feed")
    public ResponseEntity<?> searchFeed(@RequestBody ListingSearchCriteriaDTO criteriaDTO){
        return userService.searchFeed(criteriaDTO);
    }

    @PostMapping("/get-listing-status")
    public ResponseEntity<?> searchFeed(@RequestBody HashMap<String,String> listingStatus){
        return listingService.getListingsByStatus(listingStatus.get("listingStatus"),listingStatus.get("userId"));
    }

    @PostMapping("/sub-admin/update-listing-status")
    public ResponseEntity<?> updateListingStatus(@RequestBody HashMap<String,String> listingUpdateMap){

        return listingService.updateListingStatusByListingId(listingUpdateMap.get("listingStatus"),listingUpdateMap.get("listingId"));
    }


    @DeleteMapping("/sub-admin/delete-listing")
    public ResponseEntity<?> deleteListing(@RequestBody HashMap<String,String> listingUpdateMap){

        return listingService.deleteListingByListingId(listingUpdateMap.get("listingId"));
    }

    @PostMapping("/submit-tour-request")
    public ResponseEntity<?> submitTourRequest(@RequestBody TourRequestDTO tourRequestDTO){

        return listingService.createTourRequest(tourRequestDTO);

    }
    @PostMapping("/get-my-tours")
    public ResponseEntity<?> getMyTours(@RequestBody HashMap<String,String> userIdMap){

        return listingService.fetchTourRequestsForUser(userIdMap.get("userId"));

    }
    @PostMapping("/update-tour-status")
    public ResponseEntity<?> updateTourStatus(@RequestBody HashMap<String,String> updateTourStatus){


        return listingService.updateTourRequestStatus(updateTourStatus.get("tourRequestId"),updateTourStatus.get("status"));

    }

    @PostMapping("/get-notifications")
    public ResponseEntity<?> getNotifications(@RequestBody HashMap<String,String> userMap){
        return userService.getNotificationsByReceiverId(userMap.get("userId"));

    }
    @PostMapping("/add-fav-listing")
    public ResponseEntity<?> addFavListing(@RequestBody HashMap<String,String> map){
        return favoritesService.addtoFavorites(map.get("userId"),map.get("listingId"));

    }

    @PostMapping("/get-fav-listing")
    public ResponseEntity<?> getFavListing(@RequestBody HashMap<String,String> map){
        return favoritesService.getFavoritesByUserId(map.get("userId"));
    }

    @PostMapping("/remove-fav-listing")
    public ResponseEntity<?> removeFavListing(@RequestBody HashMap<String,String> map){
        return favoritesService.updateFavoriteStatus(map.get("favoriteId"),map.get("status"));

    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestBody HashMap<String,String> map){
        return userService.deleteUser(map);
    }

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody HashMap<String,String> map){
        return userService.changePassword(map);

    }

    @PostMapping("/change-email-address")
    public ResponseEntity<?> changeEmailAddress(@RequestBody HashMap<String,String> map){
        return userService.changeEmailAddress(map);

    }

    @PostMapping("/get-dashboard-count")
    public ResponseEntity<?> getDashboardCount(@RequestBody HashMap<String,String> map){
        return userService.getDashboardCount(map);

    }
    @GetMapping("/get-all-user")
    public ResponseEntity<?> getUser(){
        return userService.getAllUsers();
    }

    @PostMapping("/update-listing-insight")
    public ResponseEntity<?> updateListing(@RequestBody HashMap<String,String> insightMap){

        return insightPropertyService.updateCreateInsightProperty(insightMap.get("listingId"),insightMap.get("updateType"));

    }
    @PostMapping("/get-listing-insight")
    public ResponseEntity<?> getListingInsight(@RequestBody HashMap<String,String> getListingInsightMap){
        return insightPropertyService.getListingInsight(getListingInsightMap.get("listingId"));
    }


}

package com.redeyesncode.estatespring.realestatebackend.service;


import com.amazonaws.services.dynamodbv2.xspec.S;
import com.redeyesncode.estatespring.realestatebackend.models.Favorites;
import com.redeyesncode.estatespring.realestatebackend.models.Notification;
import com.redeyesncode.estatespring.realestatebackend.models.UserListing;
import com.redeyesncode.estatespring.realestatebackend.models.UserTable;
import com.redeyesncode.estatespring.realestatebackend.models.common.CustomStatusCodeModel;
import com.redeyesncode.estatespring.realestatebackend.models.common.NotificationType;
import com.redeyesncode.estatespring.realestatebackend.models.dto.NotificationDTO;
import com.redeyesncode.estatespring.realestatebackend.repository.FavoritesRepo;
import com.redeyesncode.estatespring.realestatebackend.repository.UserListingRepo;
import com.redeyesncode.estatespring.realestatebackend.repository.UserTableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.redeyesncode.estatespring.realestatebackend.service.ListingService.BadResponseMessage;
import static com.redeyesncode.estatespring.realestatebackend.service.ListingService.SuccessResponseMessage;

@Service
public class FavoritesService {
    @Autowired
    private FavoritesRepo favoritesRepository;

    @Autowired
    private UserTableRepo userTableRepository;

    @Autowired
    private UserListingRepo userListingRepository;

    public ResponseEntity<?> addtoFavorites(String userId, String listingId) {
        try {
            Optional<UserTable> userOptional = userTableRepository.findById(Long.valueOf(userId));
            Optional<UserListing> listingOptional = userListingRepository.findById(Long.valueOf(listingId));

            if (userOptional.isPresent() && listingOptional.isPresent()) {
                UserTable user = userOptional.get();
                UserListing listing = listingOptional.get();

//                String notificationReceiverId = String.valueOf(listing.getUser().getUserId());
//                NotificationDTO notificationDTO = new NotificationDTO();
//                notificationDTO.setNotificationType(NotificationType.VIEWED.toString());
//                notificationDTO.setReceiverId(notificationReceiverId);
//                notificationDTO.setSenderId(String.valueOf(user.getUserId()));
//                notificationDTO.setTitle("Someone added your listing to favorites");
//                notificationDTO.setUserListingId(listingId);
//                notificationDTO.setMessage("Favorites Notification");
//                NotificationService.getInstance().insertNotification(notificationDTO);



                Favorites favorite = new Favorites();
                favorite.setUser(user);
                favorite.setListing(listing);
                favorite.setStatus(Favorites.Status.ADDED);


                return ResponseEntity.ok(new CustomStatusCodeModel("200", 200, "Listing added to favorites successfully",favoritesRepository.save(favorite)));

            } else {
                return BadResponseMessage("User or listing not found !");
            }
        } catch (Exception e) {
            return BadResponseMessage(e.getMessage());
        }
    }
    public ResponseEntity<?> getFavoritesByUserId(String userId) {
        UserTable user = userTableRepository.findById(Long.valueOf(userId)).orElse(null);
        if (user != null) {
            return ResponseEntity.ok(new CustomStatusCodeModel("200",200,"Your Favorites fetched successfully!",favoritesRepository.findByUser(user)));
        }
        return BadResponseMessage("User Not Found !"); // Or handle if user is not found
    }

    public ResponseEntity<?> updateFavoriteStatus(String favoriteId, String status) {
        try {
            Long id = Long.valueOf(favoriteId);

            Optional<Favorites> favoriteOptional = favoritesRepository.findById(id);
            if (favoriteOptional.isPresent()) {
                Favorites favorite = favoriteOptional.get();

                if (status.equalsIgnoreCase("REMOVED")) {
                    favoritesRepository.delete(favorite);
                    return SuccessResponseMessage("Favorite entry removed successfully.");
                } else {
                    // Handle other status updates if needed
                    // For example, updating status to something else
                    favorite.setStatus(Favorites.Status.valueOf(status));
                    favoritesRepository.save(favorite);
                    return SuccessResponseMessage("Favorite entry status updated.");
                }
            } else {
                return BadResponseMessage("Favorite entry not found.");
            }
        } catch (Exception e) {
            return BadResponseMessage(e.getMessage());
        }
    }





}

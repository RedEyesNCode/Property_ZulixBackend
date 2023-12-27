package com.redeyesncode.estatespring.realestatebackend.service;


import com.redeyesncode.estatespring.realestatebackend.models.Favorites;
import com.redeyesncode.estatespring.realestatebackend.models.UserListing;
import com.redeyesncode.estatespring.realestatebackend.models.UserTable;
import com.redeyesncode.estatespring.realestatebackend.models.common.CustomStatusCodeModel;
import com.redeyesncode.estatespring.realestatebackend.repository.FavoritesRepo;
import com.redeyesncode.estatespring.realestatebackend.repository.UserListingRepo;
import com.redeyesncode.estatespring.realestatebackend.repository.UserTableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.redeyesncode.estatespring.realestatebackend.service.ListingService.BadResponseMessage;

@Service
public class FavoritesService {
    @Autowired
    private FavoritesRepo favoritesRepository;

    @Autowired
    private UserTableRepo userTableRepository;

    @Autowired
    private UserListingRepo userListingRepository;

    public ResponseEntity<?> addtoFavorites(Long userId, Long listingId) {
        try {
            Optional<UserTable> userOptional = userTableRepository.findById(userId);
            Optional<UserListing> listingOptional = userListingRepository.findById(listingId);

            if (userOptional.isPresent() && listingOptional.isPresent()) {
                UserTable user = userOptional.get();
                UserListing listing = listingOptional.get();

                Favorites favorite = new Favorites();
                favorite.setUser(user);
                favorite.setListing(listing);
                favorite.setStatus(Favorites.Status.ADDED);

                favoritesRepository.save(favorite);

                return ResponseEntity.ok(new CustomStatusCodeModel("200", 200, "Listing added to favorites successfully", null));
            } else {
                return BadResponseMessage("User or listing not found !");
            }
        } catch (Exception e) {
            return BadResponseMessage(e.getMessage());
        }
    }



}

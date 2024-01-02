package com.redeyesncode.estatespring.realestatebackend.service;


import com.redeyesncode.estatespring.realestatebackend.models.AddonPackage;
import com.redeyesncode.estatespring.realestatebackend.models.Subscription;
import com.redeyesncode.estatespring.realestatebackend.models.UserListing;
import com.redeyesncode.estatespring.realestatebackend.models.common.CustomStatusCodeModel;
import com.redeyesncode.estatespring.realestatebackend.models.dto.AddonDto;
import com.redeyesncode.estatespring.realestatebackend.models.dto.SubscriptionDTO;
import com.redeyesncode.estatespring.realestatebackend.repository.AddOnRepository;
import com.redeyesncode.estatespring.realestatebackend.repository.SubscriptionRepo;
import com.redeyesncode.estatespring.realestatebackend.repository.UserListingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.redeyesncode.estatespring.realestatebackend.service.ListingService.BadResponseMessage;

@Service
public class AddonService {

    @Autowired
    private AddOnRepository addOnRepository;
    @Autowired
    private SubscriptionRepo subscriptionRepo;

    @Autowired
    private UserListingRepo userListingRepo;




    public ResponseEntity<?> addPackage(AddonDto addonDto){
        AddonPackage addonPackage = new AddonPackage();
        addonPackage.setPackageName(addonDto.getPackageName());
        addonPackage.setPrice(addonDto.getPrice());
        addonPackage.setPackageMessage(addonDto.getPackageMessage());
        addOnRepository.saveAndFlush(addonPackage);
        return ResponseEntity.ok(new CustomStatusCodeModel("200",200,"Package added successfully !"));

    }


    public ResponseEntity<?> deletePackage(String packageId) {
        Optional<AddonPackage> addonPackage = addOnRepository.findById(Long.valueOf(packageId));
        if(addonPackage.isPresent()){
            addOnRepository.delete(addonPackage.get());
            return ResponseEntity.ok(new CustomStatusCodeModel("200",200,"Package deleted successfully  !"));

        }else{
            return ResponseEntity.badRequest().body(new CustomStatusCodeModel("400",400,"Package not found"));
        }


    }
    public ResponseEntity<?> addSubscription(SubscriptionDTO subscriptionDTO) {
        try {
            Subscription subscription = new Subscription();
            subscription.setName(subscriptionDTO.getName());
            subscription.setFeaturesList(subscriptionDTO.getFeaturesList());
            subscription.setTotalPrice(subscriptionDTO.getTotalPrice());

            if (subscriptionDTO.getType() != null && !subscriptionDTO.getType().isEmpty()) {
                try {
                    Subscription.SubscriptionType type = Subscription.SubscriptionType.valueOf(subscriptionDTO.getType().toUpperCase());
                    subscription.setType(type);

                    // Save the subscription entity to your repository
                    subscriptionRepo.save(subscription);

                    return ResponseEntity.ok(new CustomStatusCodeModel("201", 201, "Subscription added successfully!", subscription));
                } catch (IllegalArgumentException e) {
                    return BadResponseMessage("Invalid Subscription Type!");
                }
            } else {
                return BadResponseMessage("Subscription Type is required!");
            }
        } catch (Exception e) {
            return BadResponseMessage(e.getMessage());
        }
    }

    public ResponseEntity<?> addSubscriptionForUserListing(String listingId, String subscriptionId) {
        try {
            Long userListingId = Long.valueOf(listingId);
            Long subId = Long.valueOf(subscriptionId);

            Optional<UserListing> userListingOptional = userListingRepo.findById(userListingId);
            Optional<Subscription> subscriptionOptional = subscriptionRepo.findById(subId);

            if (userListingOptional.isPresent() && subscriptionOptional.isPresent()) {
                UserListing userListing = userListingOptional.get();
                Subscription subscription = subscriptionOptional.get();

                // Associate the subscription with the user listing
                userListing.setSubscription(subscription);

                // Save the updated user listing
                userListingRepo.save(userListing);

                return ResponseEntity.ok(new CustomStatusCodeModel("200", 200, "Subscription added to UserListing successfully!", userListing));
            } else {
                return BadResponseMessage("UserListing or Subscription not found!");
            }
        } catch (NumberFormatException e) {
            return BadResponseMessage("Invalid ID format provided!");
        } catch (Exception e) {
            return BadResponseMessage(e.getMessage());
        }
    }

    public ResponseEntity<?> removeSubscriptionFromUserListing(String listingId, String subscriptionId) {
        try {
            Long userListingId = Long.valueOf(listingId);
            Long subId = Long.valueOf(subscriptionId);

            Optional<UserListing> userListingOptional = userListingRepo.findById(userListingId);

            if (userListingOptional.isPresent()) {
                UserListing userListing = userListingOptional.get();

                if (userListing.getSubscription() != null && userListing.getSubscription().getId().equals(subId)) {
                    // Remove the subscription from the user listing
                    userListing.setSubscription(null);

                    // Save the updated user listing
                    userListingRepo.save(userListing);

                    return ResponseEntity.ok(new CustomStatusCodeModel("200", 200, "Subscription removed from UserListing successfully!", userListing));
                } else {
                    return BadResponseMessage("Subscription is not associated with the UserListing!");
                }
            } else {
                return BadResponseMessage("UserListing not found!");
            }
        } catch (NumberFormatException e) {
            return BadResponseMessage("Invalid ID format provided!");
        } catch (Exception e) {
            return BadResponseMessage(e.getMessage());
        }
    }
    public ResponseEntity<?> getPackages() {

        return ResponseEntity.ok(new CustomStatusCodeModel("200",200,"Success",addOnRepository.findAll()));

    }
    public ResponseEntity<?> getSubscriptions() {

        return ResponseEntity.ok(new CustomStatusCodeModel("200",200,"Success",subscriptionRepo.findAll()));

    }
}

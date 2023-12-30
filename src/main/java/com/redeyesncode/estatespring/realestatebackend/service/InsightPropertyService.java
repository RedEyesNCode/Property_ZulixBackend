package com.redeyesncode.estatespring.realestatebackend.service;

import com.redeyesncode.estatespring.realestatebackend.models.InsightProperty;
import com.redeyesncode.estatespring.realestatebackend.models.UserListing;
import com.redeyesncode.estatespring.realestatebackend.models.common.CustomStatusCodeModel;
import com.redeyesncode.estatespring.realestatebackend.repository.InsightPropertyRepo;
import com.redeyesncode.estatespring.realestatebackend.repository.UserListingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.redeyesncode.estatespring.realestatebackend.service.UserService.BadResponseMessage;
import static com.redeyesncode.estatespring.realestatebackend.service.UserService.SuccessResponseMessage;

@Service
public class InsightPropertyService {

    @Autowired
    private InsightPropertyRepo insightPropertyRepo;
    @Autowired
    private UserListingRepo userListingRepo;


    public ResponseEntity<?> updateCreateInsightProperty(String listingId, String updateType) {
        try {
            UserListing userListing = userListingRepo.getById(Long.valueOf(listingId));

            Optional<InsightProperty> insightOptional = insightPropertyRepo.findAllByUserListing(userListing);

            InsightProperty insightProperty = insightOptional.orElseGet(() -> {
                InsightProperty newInsight = new InsightProperty();
                // Set UserListing based on your logic
                newInsight.setUserListing(userListingRepo.getById(Long.valueOf(listingId)));
                return newInsight;
            });

            switch (updateType.toUpperCase()) {
                case "MESSAGECOUNT":
                    insightProperty.setMessageCount(insightProperty.getMessageCount() + 1);
                    break;
                case "PHONEVIEWCOUNT":
                    insightProperty.setPhoneViewCount(insightProperty.getPhoneViewCount() + 1);
                    break;
                case "IMPRESSIONCOUNT":
                    insightProperty.setImpressionCount(insightProperty.getImpressionCount() + 1);
                    break;
                default:
                    // Handle invalid updateType
                    return BadResponseMessage("Invalid updateType.");
            }

            insightPropertyRepo.save(insightProperty);
            return SuccessResponseMessage("InsightProperty updated successfully.");
        } catch (Exception e) {
            return BadResponseMessage(e.getMessage());
        }
    }

    public ResponseEntity<?> getListingInsight(String listingId) {
        try {
            UserListing userListing = userListingRepo.getById(Long.valueOf(listingId));


            Optional<InsightProperty> insightOptional = insightPropertyRepo.findAllByUserListing(userListing);
            if (insightOptional.isPresent()) {
                return ResponseEntity.ok(new CustomStatusCodeModel("200", 200, "Success", insightOptional.get()));
            } else {
                return BadResponseMessage("Insight not found !");
            }

        } catch (Exception e) {
            return BadResponseMessage(e.getMessage());
        }
    }
}

package com.redeyesncode.estatespring.realestatebackend.repository;

import com.redeyesncode.estatespring.realestatebackend.models.UserListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserListingRepo extends JpaRepository<UserListing,Long> {
    @Query("SELECT ul FROM UserListing ul WHERE ul.user.userId = :userId")
    List<UserListing> findAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM UserListing ul WHERE ul.user.userId = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);

    List<UserListing> findByListingStatus(UserListing.ListingStatus status);

    @Query("SELECT ul FROM UserListing ul WHERE ul.listingStatus = :status AND ul.user.id = :userId")
    List<UserListing> findByListingStatusAndUserId(UserListing.ListingStatus status, Long userId);
}

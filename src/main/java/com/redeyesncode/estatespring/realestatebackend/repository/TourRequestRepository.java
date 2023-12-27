package com.redeyesncode.estatespring.realestatebackend.repository;

import com.redeyesncode.estatespring.realestatebackend.models.TourRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TourRequestRepository extends JpaRepository<TourRequest,Long> {
    @Query("SELECT tr FROM TourRequest tr WHERE tr.userListing.user.id = :userId OR tr.requester.id = :userId")
    List<TourRequest> findTourRequestsForUser(Long userId);
}

package com.redeyesncode.estatespring.realestatebackend.repository;

import com.redeyesncode.estatespring.realestatebackend.models.InsightProperty;
import com.redeyesncode.estatespring.realestatebackend.models.UserListing;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InsightPropertyRepo extends JpaRepository<InsightProperty,Long> {

    @Query("SELECT i FROM InsightProperty i WHERE i.userListing = :userListing")
    Optional<InsightProperty> findAllByUserListing(@Param("userListing") UserListing userListing);

}

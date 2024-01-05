package com.redeyesncode.estatespring.realestatebackend.repository;

import com.redeyesncode.estatespring.realestatebackend.models.PaymentRecord;
import com.redeyesncode.estatespring.realestatebackend.models.UserListing;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface PaymentRecordRepo extends JpaRepository<PaymentRecord,Long> {
    @Query("SELECT COUNT(pr) > 0 FROM PaymentRecord pr WHERE pr.userListing = :userListing")
    boolean existsByUserListing(@Param("userListing") UserListing userListing);
}

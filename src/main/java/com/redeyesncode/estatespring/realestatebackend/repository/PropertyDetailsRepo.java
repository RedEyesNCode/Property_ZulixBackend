package com.redeyesncode.estatespring.realestatebackend.repository;

import com.redeyesncode.estatespring.realestatebackend.models.PropertyDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyDetailsRepo extends JpaRepository<PropertyDetails,Long> {
}

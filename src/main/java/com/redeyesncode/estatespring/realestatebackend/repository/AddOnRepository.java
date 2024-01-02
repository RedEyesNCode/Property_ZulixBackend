package com.redeyesncode.estatespring.realestatebackend.repository;

import com.redeyesncode.estatespring.realestatebackend.models.AddonPackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddOnRepository extends JpaRepository<AddonPackage,Long> {
}

package com.redeyesncode.estatespring.realestatebackend.models.common;


import com.redeyesncode.estatespring.realestatebackend.models.AddonPackage;
import com.redeyesncode.estatespring.realestatebackend.models.Subscription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseListingBill {
    private List<AddonPackage> addons;

    private Subscription subscription;

    private BigDecimal totalBill;


}

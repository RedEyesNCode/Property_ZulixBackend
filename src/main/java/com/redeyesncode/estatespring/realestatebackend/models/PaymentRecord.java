package com.redeyesncode.estatespring.realestatebackend.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_listing_id")
    private UserListing userListing;

    private BigDecimal amountPaid;
    private String paymentMethod; // For example, 'Stripe'
    private String paymentStatus; // Successful, Pending, Failed, etc.
    private LocalDateTime paymentDate;

    private String paymentIntentId;



    @CreationTimestamp
    private String paymentRecordTime;
}

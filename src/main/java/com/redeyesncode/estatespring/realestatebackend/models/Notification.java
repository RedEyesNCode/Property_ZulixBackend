package com.redeyesncode.estatespring.realestatebackend.models;


import com.redeyesncode.estatespring.realestatebackend.models.common.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String message;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UserTable receiver;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserTable sender;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @ManyToOne
    @JoinColumn(name = "user_listing_id")
    private UserListing userListing;

    @CreationTimestamp
    private String createdTimestamp;
}

package com.redeyesncode.estatespring.realestatebackend.models;


import lombok.Data;

@Data
public class NotificationDTO {
    private String senderId;
    private String receiverId;
    private String userListingId;
    private String title;
    private String message;
    private String notificationType;
}

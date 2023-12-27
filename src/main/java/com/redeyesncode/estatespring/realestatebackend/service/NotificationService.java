package com.redeyesncode.estatespring.realestatebackend.service;

import com.redeyesncode.estatespring.realestatebackend.models.*;
import com.redeyesncode.estatespring.realestatebackend.repository.NotificationRepo;
import com.redeyesncode.estatespring.realestatebackend.repository.UserListingRepo;
import com.redeyesncode.estatespring.realestatebackend.repository.UserTableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {
    private static NotificationService instance;

    @Autowired
    private NotificationRepo notificationRepository;

    @Autowired
    private UserTableRepo userTableRepo;

    @Autowired
    private UserListingRepo userListingRepo;



    // Private constructor to prevent instantiation
    private NotificationService() {
    }

    // Singleton getInstance method
    public static synchronized NotificationService getInstance() {
        if (instance == null) {
            instance = new NotificationService();
        }
        return instance;
    }

    // Method to insert a notification
    public void insertNotification(NotificationDTO notificationDTO) {
        Notification notification = new Notification();

        UserTable sender = userTableRepo.findById(Long.valueOf(notificationDTO.getSenderId())).orElse(null);
        UserTable receiver = userTableRepo.findById(Long.valueOf(notificationDTO.getReceiverId())).orElse(null);
        UserListing userListing = userListingRepo.findById(Long.valueOf(notificationDTO.getUserListingId())).orElse(null);

        if (sender != null && receiver != null && userListing != null) {
            notification.setSender(sender);
            notification.setReceiver(receiver);
            notification.setUserListing(userListing);
            notification.setTitle(notificationDTO.getTitle());
            notification.setMessage(notificationDTO.getMessage());
            notification.setNotificationType(NotificationType.valueOf(notificationDTO.getNotificationType()));
            notification.setCreatedTimestamp(String.valueOf(LocalDateTime.now()));

            notificationRepository.save(notification);
        } else {
            // Handle scenarios where entities are not found
            return;
            // For example: throw an exception, log an error, or perform appropriate error handling
        }
    }

    // Other methods for notification management, if needed
}

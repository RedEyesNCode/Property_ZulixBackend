package com.redeyesncode.estatespring.realestatebackend.service;

import com.redeyesncode.estatespring.realestatebackend.models.Notification;
import com.redeyesncode.estatespring.realestatebackend.models.NotificationType;
import com.redeyesncode.estatespring.realestatebackend.models.UserTable;
import com.redeyesncode.estatespring.realestatebackend.repository.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {
    private static NotificationService instance;

    @Autowired
    private NotificationRepo notificationRepository;

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
    public void insertNotification(UserTable sender, UserTable receiver, String title, String message, NotificationType type) {
        Notification notification = new Notification();
        notification.setSender(sender);
        notification.setReceiver(receiver);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setNotificationType(type);
        notification.setCreatedTimestamp(String.valueOf(LocalDateTime.now()));

        notificationRepository.save(notification);
    }

    // Other methods for notification management, if needed
}

package com.redeyesncode.estatespring.realestatebackend.repository;

import com.redeyesncode.estatespring.realestatebackend.models.Notification;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification,Long> {

}

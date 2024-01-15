package com.redeyesncode.estatespring.realestatebackend.repository;

import com.redeyesncode.estatespring.realestatebackend.models.ChatMessage;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepo extends JpaRepository<ChatMessage,Long> {

    @Query("SELECT c FROM ChatMessage c WHERE c.roomName = :roomName")
    List<ChatMessage> findByRoomName(String roomName);


}

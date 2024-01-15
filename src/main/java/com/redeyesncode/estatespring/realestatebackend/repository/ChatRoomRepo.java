package com.redeyesncode.estatespring.realestatebackend.repository;

import com.redeyesncode.estatespring.realestatebackend.models.ChatRoomTable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepo extends JpaRepository<ChatRoomTable,Long> {
//    @Query("SELECT n FROM ChatRoomTable n WHERE n.roomName = :roomName")
//    List<ChatRoomTable> findEntityByRoomName(@Param("roomName") String roomName);
//
//    @Query("SELECT n FROM ChatRoomTable n WHERE n.clientUserId = :clientUserId")
//    List<ChatRoomTable> findEntityByClientID(@Param("clientUserId") Long roomName);
//    @Query("SELECT n FROM ChatRoomTable n WHERE n.currentUserId = :currentUserId")
//    List<ChatRoomTable> findEntityBySenderID(@Param("currentUserId") Long roomName);


    @Query("SELECT cr FROM ChatRoomTable cr WHERE cr.userListingId = :userListingId")
    List<ChatRoomTable> findByUserListingId(String userListingId);

}

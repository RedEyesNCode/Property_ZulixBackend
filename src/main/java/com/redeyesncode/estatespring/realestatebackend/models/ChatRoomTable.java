package com.redeyesncode.estatespring.realestatebackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ChatRoom")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ChatRoomTable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_room_bumble_sequence")
    private Long id;

    private String roomName;
    // roomName would be currentUserId+clientUserId+geetu_ashu

    private String currentUserId;

    private String clientUserId;

    private String userListingId;




    public ChatRoomTable(String roomName, String currentUserId, String clientUserId,String userListingId) {
        this.roomName = roomName;
        this.currentUserId = currentUserId;
        this.userListingId = userListingId;

        this.clientUserId = clientUserId;
    }


}

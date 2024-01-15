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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;
    // roomName would be currentUserId+clientUserId+geetu_ashu

    @ManyToOne
    private UserTable currentUser;

    @ManyToOne
    private UserTable clientUser;



    private String userListingId;


    public ChatRoomTable(String roomName, UserTable currentUser, UserTable clientUser, String listingId) {

        this.roomName = roomName;
        this.clientUser = clientUser;
        this.currentUser = currentUser;
        this.userListingId = listingId;


    }
}

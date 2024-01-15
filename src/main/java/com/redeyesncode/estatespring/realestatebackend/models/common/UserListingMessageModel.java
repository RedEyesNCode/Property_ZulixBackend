package com.redeyesncode.estatespring.realestatebackend.models.common;

import com.redeyesncode.estatespring.realestatebackend.models.ChatMessage;
import com.redeyesncode.estatespring.realestatebackend.models.ChatRoomTable;
import com.redeyesncode.estatespring.realestatebackend.models.UserListing;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListingMessageModel {

    private UserListing listing;

    private ChatMessage message;

    private ChatRoomTable chatRoom;



}

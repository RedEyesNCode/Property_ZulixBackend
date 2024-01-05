package com.redeyesncode.estatespring.realestatebackend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    private String roomName;
    private String senderId;
    private String receiverId;
    private String text;
}

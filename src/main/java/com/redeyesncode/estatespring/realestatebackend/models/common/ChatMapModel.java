package com.redeyesncode.estatespring.realestatebackend.models.common;

public class ChatMapModel {
    private String roomName;

    private Boolean isNewChat;

    public ChatMapModel(String roomName, Boolean isNewChat) {
        this.roomName = roomName;
        this.isNewChat = isNewChat;
    }
}

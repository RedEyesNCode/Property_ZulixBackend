package com.redeyesncode.estatespring.realestatebackend.sockets.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.redeyesncode.estatespring.realestatebackend.sockets.config.DeserializerSocket;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
public class MessageSocket {

    public MessageSocket deserializeJsonString() {
        ObjectMapper mapper = new ObjectMapper();
        String personJson = "{" +
                "\"type\": \"SERVER\"," +
                "\"message\": \"HII-SERVER\"," +
                "\"room\": \"a\""  +
                "}";

        try {
            MessageSocket person = mapper.readValue(personJson, MessageSocket.class);
            System.out.println("Email Account: " + new Gson().toJson(person));
            return person;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


    public String type;

    public String message;

    public String room;


    public String senderId;

    public String receiverId;


    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public MessageSocket() {
    }

    public MessageSocket(String json){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(MessageSocket.class, new DeserializerSocket())
                .create();
        MessageSocket tempObj = gson.fromJson(json,MessageSocket.class);
        this.message = tempObj.getMessage();
        this.type = tempObj.getType();
        this.room = tempObj.getRoom();
        this.receiverId = tempObj.getReceiverId();
        this.senderId = tempObj.getSenderId();





    }

    public MessageSocket(String type, String message, String room) {
        this.type = type;
        this.message = message;
        this.room = room;
    }

    public MessageSocket(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public MessageSocket(String type, String message, String room, String senderId, String receiverId) {
        this.type = type;
        this.message = message;
        this.room = room;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }
}

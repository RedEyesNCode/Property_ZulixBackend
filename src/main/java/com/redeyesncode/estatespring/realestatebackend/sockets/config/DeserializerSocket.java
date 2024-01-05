package com.redeyesncode.estatespring.realestatebackend.sockets.config;




import com.google.gson.*;
import com.redeyesncode.estatespring.realestatebackend.sockets.models.MessageSocket;

import java.lang.reflect.Type;

public class DeserializerSocket  implements JsonDeserializer<MessageSocket> {


    @Override
    public MessageSocket deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject obj = jsonElement.getAsJsonObject();

        MessageSocket msgSocket = new MessageSocket();
        msgSocket.setMessage(obj.get("message").getAsString());
        msgSocket.setType(obj.get("type").getAsString());
        msgSocket.setRoom(obj.get("room").getAsString());
        msgSocket.setSenderId(obj.get("senderId").getAsString());
        msgSocket.setReceiverId(obj.get("receiverId").getAsString());

        return msgSocket;
    }
}

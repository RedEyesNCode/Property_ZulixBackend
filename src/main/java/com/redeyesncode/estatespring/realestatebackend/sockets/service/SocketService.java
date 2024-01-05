package com.redeyesncode.estatespring.realestatebackend.sockets.service;


import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.redeyesncode.estatespring.realestatebackend.models.ChatMessage;
import com.redeyesncode.estatespring.realestatebackend.models.dto.ChatMessageDTO;
import com.redeyesncode.estatespring.realestatebackend.repository.ChatMessageRepo;
import com.redeyesncode.estatespring.realestatebackend.repository.ChatRoomRepo;
import com.redeyesncode.estatespring.realestatebackend.sockets.models.MessageSocket;
import com.redeyesncode.estatespring.realestatebackend.sockets.models.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SocketService {

    @Autowired
    ChatMessageRepo chatMessageRepo;

    @Autowired
    ChatRoomRepo chatRoomRepo;



    public void sendMessage(String room, String eventName, SocketIOClient senderClient, String message, String senderType, String senderId, String receiverId, AckRequest ackSender) {
        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent(eventName,
                        new MessageSocket(senderType,message,room,senderId,receiverId));
                ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
                chatMessageDTO.setText(message);
                chatMessageDTO.setRoomName(room);
                chatMessageDTO.setReceiverId(receiverId);
                chatMessageDTO.setSenderId(senderId);
                saveMessage(chatMessageDTO);



            }
            log.info("senderId  -->"+senderId);
            log.info("receiverId  -->"+receiverId);

            log.info("CLIENT SESSION ID -->"+client.getSessionId().toString());
            log.info("SENDER SESSION ID -->"+senderClient.getSessionId().toString());
            log.info("ROOM ID -->"+room);
        }
    }

    public void saveMessage(ChatMessageDTO messageDTO) {
        // Convert ChatMessageDTO to ChatMessage entity
        ChatMessage message = convertDTOToEntity(messageDTO);
        chatMessageRepo.save(message);


        // Backend will save only 10 last messages for each room.

        int messageLimit = 10;
//        List<ChatMessage> messages = chatMessageRepo.findMessagesForRoom(message.getRoomName());

//        if(messages.isEmpty()){
//            chatMessageRepo.save(message);
//
//        } else if (messages.size()<=messageLimit) {
//
//        }else{
//            int messagesToDelete = messages.size() - messageLimit;
//            for (int i = 0; i < messagesToDelete; i++) {
//                chatMessageRepo.delete(messages.get(i));
//            }
//        }

        // Save the message

        chatMessageRepo.flush();
    }





    private ChatMessage convertDTOToEntity(ChatMessageDTO messageDTO) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRoomName(messageDTO.getRoomName());
        chatMessage.setText(messageDTO.getText());
        chatMessage.setReceiver(String.valueOf(messageDTO.getReceiverId()));
        chatMessage.setSender(String.valueOf(messageDTO.getSenderId()));

        return chatMessage;


    }

    public void sendMessageOG(String room, String eventName, SocketIOClient senderClient, String message) {
        for (
                SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent(eventName,
                        new MessageSocket(MessageType.SERVER.toString(), message,room));
                client.sendEvent("get_message",
                        new MessageSocket(MessageType.SERVER.toString(), message,room));
                client.sendEvent("send_message",
                        new MessageSocket(MessageType.SERVER.toString(), message,room));
            }
        }
    }

}

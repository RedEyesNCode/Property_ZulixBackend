package com.redeyesncode.estatespring.realestatebackend.sockets;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.redeyesncode.estatespring.realestatebackend.sockets.models.MessageSocket;
import com.redeyesncode.estatespring.realestatebackend.sockets.service.SocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocketModule {


    @Autowired
    private final SocketIOServer server;

    @Autowired
    private final SocketService socketService;


    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_message", MessageSocket.class, onChatReceived());
        server.addEventListener("send_ashu", MessageSocket.class, onChatReceived());
    }


    private DataListener<MessageSocket> onChatReceived() {


        return (senderClient, data, ackSender) -> {
            log.info("onChatReceived() "+data.toString()+" Socket address :"+senderClient.getRemoteAddress().toString()+" <--SESSION ID--> "+senderClient.getSessionId().toString());
//            socketService.sendMessageOG(data.getRoom(),"get_message", senderClient, data.getMessage());
            socketService.sendMessage(data.getRoom(),"get_message", senderClient, data.getMessage(),data.getType(),data.getSenderId(),data.getReceiverId(),ackSender);
        };
    }


    private ConnectListener onConnected() {
        return (client) -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            client.joinRoom(room);
            log.info("Socket ID[{}]  Connected to socket Room is "+room, client.getSessionId().toString());

        };

    }

    private DisconnectListener onDisconnected() {
        return client -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");

            log.info("Client[{}] - Disconnected from socket in Room "+room, client.getSessionId().toString());
        };
    }

}
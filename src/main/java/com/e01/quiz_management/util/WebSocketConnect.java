package com.e01.quiz_management.util;

import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.websocket.Message;
import com.e01.quiz_management.websocket.MyWebSocketStompClient;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.lang.reflect.Type;

public class WebSocketConnect {

    private static WebSocketConnect instance;
    private final static String WEB_SOCKET_URL = "ws://13.212.194.205/chat";
//    private final static String WEB_SOCKET_URL = "ws://localhost:8080/chat";


    private MyWebSocketStompClient stompClient;

    private WebSocketConnect() {
        stompClient = new MyWebSocketStompClient(new StandardWebSocketClient());
    }

    public static WebSocketConnect getInstance() {
        if (instance == null) {
            instance = new WebSocketConnect();
        }
        return instance;
    }

    public void joinTest(Long testId) {
        stompClient.connectAsync(WEB_SOCKET_URL, new StompSessionHandlerAdapter() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Message.class;
            }

            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
//                session.subscribe("/topic/test/"+testId, this);
                System.out.println("Join test " + testId );
//                session.subscribe("/topic/test/1", this);
//                session.send("/app/test/" + testId+"/join", new Message(RequestAPI.getInstance().getUser().getName(), new Test()));
//                session.subscribe("/topic/test/1", this);
                session.send("/app/test/"+testId+"/join", new Message("hi", new Test()));
            }
        });
//        new Scanner(System.in).nextLine(); // Don't close immediately.
    }

    public void subscribeToTest(Long testId,  WebSocketConnectHandler connectHandler){
        stompClient.connectAsync(WEB_SOCKET_URL, new StompSessionHandlerAdapter() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Message.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Received");
                connectHandler.onReceived(payload);
            }

            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                session.subscribe("/topic/test/"+testId, this);
                connectHandler.onConnected();
                System.out.println("Subscribed to test " + testId);
            }
        });
    }



}

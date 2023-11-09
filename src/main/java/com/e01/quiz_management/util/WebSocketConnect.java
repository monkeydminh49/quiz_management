package com.e01.quiz_management.util;

import com.e01.quiz_management.websocket.Message;
import com.e01.quiz_management.websocket.MyStompSessionHandler;
import com.e01.quiz_management.websocket.MyWebSocketStompClient;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.lang.reflect.Type;
import java.util.Scanner;

public class WebSocketConnect {

    private static WebSocketConnect instance;
    private final static String WEB_SOCKET_URL = "ws://localhost:8080/chat";

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

    public void connectToTest(Long testId, WebSocketConnectHandler connectHandler) {
        StompSessionHandler sessionHandler = new MyStompSessionHandler();
//        connectHandler.onReceived(new Message());
        stompClient.connectAsync(WEB_SOCKET_URL, new StompSessionHandlerAdapter() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Message.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                connectHandler.onReceived(payload);
            }

            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                session.subscribe("/topic/test/"+testId, this);
                session.send("/app/test/" + testId+"/join", new Message("Minh", "hello"));
            }

            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                super.handleException(session, command, headers, payload, exception);
            }

            @Override
            public void handleTransportError(StompSession session, Throwable exception) {
                super.handleTransportError(session, exception);
            }
        });
        new Scanner(System.in).nextLine(); // Don't close immediately.
    }

}

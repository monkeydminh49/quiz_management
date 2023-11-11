package com.e01.quiz_management.websocket;

import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.util.WebSocketConnect;
import com.e01.quiz_management.util.WebSocketConnectHandler;
import org.springframework.messaging.simp.stomp.*;

import java.lang.reflect.Type;
import java.util.Scanner;

/**
 * Stand alone WebSocketStompClient.
 *
 */
public class StompClient {

    private static String URL = "ws://localhost:8080/chat";

    public static void main(String[] args) {
        MyWebSocketStompClient stompClient = MyWebSocketStompClient.getInstance();
//        StompSessionHandler sessionHandler = new MyStompSessionHandler();
//        stompClient.connectAsync(URL, sessionHandler);
        WebSocketConnect connection = WebSocketConnect.getInstance();
        connection.subscribeToTest(1L, new WebSocketConnectHandler() {
            @Override
            public void onReceived(Object payload) {
                System.out.println("hoho");
            }
        });
        new Scanner(System.in).nextLine(); // Don't close immediately.

        StompSessionHandlerAdapter a = new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                System.out.println("connected to private");
                session.subscribe("/topic/test/1", this);
                session.send("/app/test/1/join", new Message("hi", new Test()));
            }

            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                System.out.println("exception");
            }

            @Override
            public void handleTransportError(StompSession session, Throwable exception) {

            }

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Message.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("received from private");
                Message msg = (Message) payload;
                System.out.println("Received from : " + msg.getFrom());
            }
        };
        stompClient.connectAsync(URL, a);

        new Scanner(System.in).nextLine(); // Don't close immediately.


        connection.joinTest(1L);
        new Scanner(System.in).nextLine();
        connection.subscribeToTest(1L, new WebSocketConnectHandler() {
            @Override
            public void onReceived(Object payload) {
                System.out.println("hoho");
            }
        });
        new Scanner(System.in).nextLine();

    }
}
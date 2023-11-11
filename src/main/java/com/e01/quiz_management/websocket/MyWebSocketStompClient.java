package com.e01.quiz_management.websocket;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class MyWebSocketStompClient  extends WebSocketStompClient {
    private static MyWebSocketStompClient instance;

    public static MyWebSocketStompClient getInstance() {
        if (instance == null) {
            WebSocketClient client = new StandardWebSocketClient();
            instance = new MyWebSocketStompClient(client);
//            instance.setMessageConverter(new MappingJackson2MessageConverter());
        }
        return instance;
    }


    public MyWebSocketStompClient(WebSocketClient webSocketClient) {
        super(webSocketClient);
        this.setMessageConverter(new MappingJackson2MessageConverter());
    }

}

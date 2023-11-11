package com.e01.quiz_management.util;

public interface WebSocketConnectHandler {
    default void onConnected(){
        System.out.println("Connected");
    };
    void onReceived(Object payload);
}

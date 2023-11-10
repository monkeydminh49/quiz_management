package com.e01.quiz_management.websocket;

public class Message {

    private String from;
    private Object data;

    public Message() {
    }

    public Message(String from, Object data) {
        this.from = from;
        this.data = data;
    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
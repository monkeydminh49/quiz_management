package com.e01.quiz_management.websocket;

import com.e01.quiz_management.util.EMessageType;

public class Message {

    private String from;
    private Object data;

    private EMessageType type;
    public Message() {
    }

    public Message(String from, Object data) {
        this.from = from;
        this.data = data;
        this.type = EMessageType.NUM_LIVE_PARTICIPANT;
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

    public EMessageType getType() {
        return type;
    }

    public void setType(EMessageType type) {
        this.type = type;
    }
}
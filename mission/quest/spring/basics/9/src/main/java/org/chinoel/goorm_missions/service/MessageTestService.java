package org.chinoel.goorm_missions.service;

public class MessageTestService {
    private String message;

    public MessageTestService(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
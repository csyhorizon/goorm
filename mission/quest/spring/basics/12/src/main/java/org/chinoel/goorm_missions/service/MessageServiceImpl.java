package org.chinoel.goorm_missions.service;

import org.chinoel.goorm_missions.service.impl.Message;

public class MessageServiceImpl implements MessageService{
    private Message message;

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public Message getMessage() {
        return message;
    }
}

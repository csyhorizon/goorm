package org.chinoel.goorm_missions.component;

import org.chinoel.goorm_missions.service.Message;
import org.chinoel.goorm_missions.service.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessagePrinter {

    private final Message message;
    private final MessageType messageType;

    @Autowired
    public MessagePrinter(Message message, MessageType messageType) {
        this.message = message;
        this.messageType = messageType;
    }

    public MessagePrinter(Message message) {
        this.message = message;
        this.messageType = null;
    }

    public void printMessage() {
        if (messageType != null) {
            System.out.println(message.getMessage() + " " + messageType.getType());
        } else {
            System.out.println(message.getMessage());
        }
    }
}
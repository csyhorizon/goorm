package org.chinoel.goorm_missions.service;

import org.chinoel.goorm_missions.service.impl.Message;

public class MessagePrinter {
    private MessageService messageService;

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void printMessage() {
        Message message = messageService.getMessage();
        System.out.println(message.getType().getName() + ": " + message.getContent());
    }
}

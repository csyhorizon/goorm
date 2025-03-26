package org.chinoel.goorm_missions;

import static org.junit.jupiter.api.Assertions.*;

import org.chinoel.goorm_missions.component.MessagePrinter;
import org.chinoel.goorm_missions.config.AppConfig;
import org.chinoel.goorm_missions.service.Message;
import org.chinoel.goorm_missions.service.impl.HelloMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(AppConfig.class)
class GoormMissionsannotation {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private MessagePrinter printer;

    @Autowired
    private Message message;

    @Test
    void print() {
        printer.printMessage();
    }

    @Test
    void printMessageTest() {
        assertTrue(message instanceof HelloMessage);
    }

    @Test
    void shouldUseHelloMessageImplementaion() {
        assertEquals(HelloMessage.class, context.getBean(Message.class).getClass());
    }
}
package org.chinoel.goorm_missions;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.chinoel.goorm_missions.service.MessagePrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

class GoormMissionsApplicationTest {
    ApplicationContext context;

    @BeforeEach
    void setUp() {
        context = new GenericXmlApplicationContext("spring-context.xml");
    }

    @Test
    @DisplayName("제대로 빈이 등록되고 출력되었나?")
    void test() {
        MessagePrinter printer = context.getBean("messagePrinter", MessagePrinter.class);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        printer.printMessage();

        assertEquals("INFO: XML From Test", outputStream.toString().trim());
    }
}
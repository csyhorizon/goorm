package org.chinoel.goorm_missions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.chinoel.goorm_missions.fixed.service.ServiceA;
import org.chinoel.goorm_missions.fixed.service.ServiceB;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootTest
class GoormSolvedTest {

    @Configuration
    @Import({ServiceA.class, ServiceB.class})
    static class TestConfig {}

    @Autowired
    private ServiceA serviceA;

    @Autowired
    private ServiceB serviceB;

    @Test
    @DisplayName("순환 참조 해결 : 인터페이스")
    void testMessage() {
        assertNotNull(serviceA);
        assertNotNull(serviceB);

        assertEquals("Message from A", serviceA.getMessageA());
        assertEquals("Message from B", serviceB.getMessageB());
    }
}
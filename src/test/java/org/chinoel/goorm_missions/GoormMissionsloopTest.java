package org.chinoel.goorm_missions;

import static org.junit.jupiter.api.Assertions.*;

import org.chinoel.goorm_missions.loop.service.ServiceA;
import org.chinoel.goorm_missions.loop.service.ServiceB;
import org.chinoel.goorm_missions.loop.service.controller.TestController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

class GoormMissionsloopTest {

    @Configuration
    @Import({ServiceA.class, ServiceB.class})
    static class LoopTestConfig {}

    @Test
    @DisplayName("순환 참조 오류 확인")
    void loofTest() {
        assertThrows(UnsatisfiedDependencyException.class, () -> {
            new AnnotationConfigApplicationContext(TestController.class);
        });
    }
}
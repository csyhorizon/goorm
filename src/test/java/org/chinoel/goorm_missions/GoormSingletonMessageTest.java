package org.chinoel.goorm_missions;

import org.chinoel.goorm_missions.config.AppConfig;
import org.chinoel.goorm_missions.service.MessageTestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class GoormSingletonMessageTest {
    AnnotationConfigApplicationContext context;

    @BeforeEach
    void setUp() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
    }


    @Test
    @DisplayName("싱글톤 테스트")
    void singleTon() {
        MessageTestService singleton1 = context.getBean("singletonMessageService", MessageTestService.class);
        MessageTestService singleton2 = context.getBean("singletonMessageService", MessageTestService.class);

        Assertions.assertSame(singleton1, singleton2);
    }

    @Test
    @DisplayName("프로토타입 테스트")
    void protoType() {
        MessageTestService protoType1 = context.getBean("prototypeMessageService", MessageTestService.class);
        MessageTestService protoType2 = context.getBean("prototypeMessageService", MessageTestService.class);

        Assertions.assertNotSame(protoType1, protoType2);
    }
}
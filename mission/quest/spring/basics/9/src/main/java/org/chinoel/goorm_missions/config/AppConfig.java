package org.chinoel.goorm_missions.config;

import org.chinoel.goorm_missions.service.MessageTestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean
    @Scope("singleton")
    public MessageTestService singletonMessageService() {
        return new MessageTestService("singleton Initial Message");
    }

    @Bean
    @Scope("prototype")
    public MessageTestService prototypeMessageService() {
        return new MessageTestService("prototype Initial Message");
    }
}
package org.chinoel.goorm_missions.config;

import org.chinoel.goorm_missions.service.DeviceService;
import org.chinoel.goorm_missions.service.impl.ComputerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.chinoel.goorm_missions")
public class AppConfig {

    @Bean
    public DeviceService computerService() {
        return new ComputerServiceImpl("PC Z-", 1);
    }

    @Bean
    public DeviceService deviceService() {
        return new ComputerServiceImpl("mobile A-", 2);
    }
}
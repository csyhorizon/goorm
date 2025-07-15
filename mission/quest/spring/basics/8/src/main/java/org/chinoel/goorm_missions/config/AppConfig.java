package org.chinoel.goorm_missions.config;

import org.chinoel.goorm_missions.service.DeviceService;
import org.chinoel.goorm_missions.service.impl.ComputerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

@Configuration
@ComponentScan(basePackages = "org.chinoel.goorm_missions")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AppConfig {

    @Bean(name = "computerService")
    public DeviceService computerService() {
        return new ComputerServiceImpl("PC Z-", 1);
    }

    @Bean(name = "mobileService")
    public DeviceService deviceService() {
        return new ComputerServiceImpl("mobile A-", 2);
    }
}
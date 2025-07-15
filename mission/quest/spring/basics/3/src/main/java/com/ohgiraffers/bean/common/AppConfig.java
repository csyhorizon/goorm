package com.ohgiraffers.bean.common;

import com.ohgiraffers.bean.service.CarService;
import com.ohgiraffers.bean.service.impl.BrokenCarServiceImpl;
import com.ohgiraffers.bean.service.impl.RacingCarServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.ohgiraffers")
public class AppConfig {

    @Bean("brokenCarServiceImpl")
    public CarService brokenCarService() {
        return new BrokenCarServiceImpl();
    }

    @Bean("racingCarServiceImpl")
    public CarService racingCarService() {
        return new RacingCarServiceImpl();
    }
}

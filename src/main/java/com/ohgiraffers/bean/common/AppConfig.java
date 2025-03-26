package com.ohgiraffers.bean.common;

import com.ohgiraffers.bean.service.CarService;
import com.ohgiraffers.bean.service.impl.BrokenCarServiceImpl;
import com.ohgiraffers.bean.service.impl.RacingCarServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${car.racingcar.name}")
    private String racingCarName;

    @Value("${car.racingcar.price}")
    private int racingCarPrice;

    @Value("${car.brokencar.name}")
    private String brokenCarName;

    @Value("${car.brokencar.price}")
    private int brokenCarPrice;

    @Bean
    public CarService racingCar() {
        return new RacingCarServiceImpl(racingCarName, racingCarPrice);
    }
    @Bean
    public CarService brokenCar() {
        return new BrokenCarServiceImpl(brokenCarName, brokenCarPrice);
    }
}

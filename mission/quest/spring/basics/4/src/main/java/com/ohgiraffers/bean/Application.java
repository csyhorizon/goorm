package com.ohgiraffers.bean;

import com.ohgiraffers.bean.common.AppConfig;
import com.ohgiraffers.bean.service.CarService;
import com.ohgiraffers.bean.service.impl.BrokenCarServiceImpl;
import com.ohgiraffers.bean.service.impl.RacingCarServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        CarService racingCar = context.getBean("racingCar", RacingCarServiceImpl.class);
        CarService brokenCar = context.getBean("brokenCar", BrokenCarServiceImpl.class);

        System.out.println("주행 차 = " + racingCar);
        System.out.println("침수 차 = " + brokenCar);
    }
}

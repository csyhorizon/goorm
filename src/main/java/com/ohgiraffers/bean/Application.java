package com.ohgiraffers.bean;

import com.ohgiraffers.bean.common.AppConfig;
import com.ohgiraffers.bean.service.CarService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        CarService primaryCar = context.getBean(CarService.class);
        System.out.print("Primary Car Sound : ");
        primaryCar.playSound();

        CarService brokenCar = context.getBean("brokencarserviceimpl",
                CarService.class);
        System.out.print("Broken Car Sound : ");
        brokenCar.playSound();
    }
}

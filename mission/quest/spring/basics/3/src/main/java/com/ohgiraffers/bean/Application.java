package com.ohgiraffers.bean;

import com.ohgiraffers.bean.common.AppConfig;
import com.ohgiraffers.bean.config.CarConfiguration;
import com.ohgiraffers.bean.service.CarService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        CarConfiguration carConfig = context.getBean(CarConfiguration.class);
        carConfig.runCars();
    }
}

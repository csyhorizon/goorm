package com.ohgiraffers.aop;

import com.ohgiraffers.aop.service.BuyService;
import java.util.stream.Stream;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext("com.ohgiraffers.aop");

        BuyService buyService = context.getBean(BuyService.class);

        System.out.println(buyService.butItems());

        try {
            buyService.buyItem(1L, 1);
            buyService.buyItem(1L, 1);
            buyService.buyItem(1L, 2);
        } catch (Exception e) {
            System.out.println("예외 발생 : " + e.getMessage());
        }

        System.out.println(buyService.butItems());
    }
}

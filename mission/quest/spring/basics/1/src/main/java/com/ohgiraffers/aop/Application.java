package com.ohgiraffers.aop;

import com.ohgiraffers.aop.service.ProductService;
import com.ohgiraffers.aop.utils.DatabaseConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(DatabaseConfig.class);

        ProductService productService = context.getBean(ProductService.class);

        System.out.println("==== 상품 구매 시작 : 목록 ====");
        System.out.println(productService.getAllProducts());

        tryPurchase(() -> productService.buyItem(1L, 2));
        tryPurchase(() -> productService.buyItem(1L, 10));

        System.out.println("==== 상품 구매 종료 : 목록 ====");
        System.out.println(productService.getAllProducts());
    }

    private static void tryPurchase(Runnable purchaseAction) {
        try {
            purchaseAction.run();
            System.out.println("구매 성공!");
        } catch (Exception e) {
            System.out.println("구매 실패: " + e.getMessage());
        }
    }
}

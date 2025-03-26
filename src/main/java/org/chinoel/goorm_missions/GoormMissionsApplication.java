package org.chinoel.goorm_missions;

import org.chinoel.goorm_missions.config.AppConfig;
import org.chinoel.goorm_missions.service.DeviceService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class GoormMissionsApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("==== 컨텍스트 초기화 ====");

        DeviceService computer = context.getBean("computerService", DeviceService.class);
        System.out.println(computer);

        computer.destroy();
        System.out.println("==== 컨텍스트 종료 ====");
        context.close();
    }

}

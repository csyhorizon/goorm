package com.ohgiraffers.bean.config;

import com.ohgiraffers.bean.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CarConfiguration {

    private final CarService brokenCar;
    private final CarService racingCar;

    @Autowired
    public CarConfiguration(@Qualifier("brokenCarServiceImpl") CarService brokenCar,
                            @Qualifier("racingCarServiceImpl") CarService racingCar) {
        this.brokenCar = brokenCar;
        this.racingCar = racingCar;
    }

    public void runCars() {
        System.out.print("망가진 차 : ");
        brokenCar.playSound();
        System.out.print("스포츠 카 : ");
        racingCar.playSound();
    }
}

package com.ohgiraffers.bean.service.impl;

import com.ohgiraffers.bean.service.CarService;

public class BrokenCarServiceImpl implements CarService {

    @Override
    public void playSound() {
        System.out.println("끼리..끼리리리릭...뿌루루");
    }
}

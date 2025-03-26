package com.ohgiraffers.bean.service.impl;

import com.ohgiraffers.bean.service.CarService;

public class BrokenCarServiceImpl extends CarService {

    public BrokenCarServiceImpl(String name, int price) {
        super(name, price);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
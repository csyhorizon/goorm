package com.ohgiraffers.bean.service;

public abstract class CarService {

    private String name;
    private int price;

    private CarService() {}

    public CarService(String name, int price) {
        super();
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CarService{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}

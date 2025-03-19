package com.ohgiraffers.aop.dto;

public class ProductDTO {

    private String itemName;
    private int price;
    private int itemCount;

    public ProductDTO() {};

    public ProductDTO(String itemName, int price, int itemCount) {
        this.itemName = itemName;
        this.price = price;
        this.itemCount = itemCount;
    }

    public void buyItem(int itemCount) {
        this.itemCount -= itemCount;
    }

    public int getItemCount() {
        return itemCount;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "itemName='" + itemName + '\'' +
                ", price=" + price +
                ", itemCount=" + itemCount +
                '}';
    }
}

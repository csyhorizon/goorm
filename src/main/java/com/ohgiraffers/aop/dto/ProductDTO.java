package com.ohgiraffers.aop.dto;

public class ProductDTO {
    private Long id;
    private String itemName;
    private int price;
    private int itemCount;

    protected ProductDTO() {}

    public ProductDTO(Long id, String itemName, int price, int imteCount) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.itemCount = imteCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public void buyItem(int count) {
        if (this.itemCount < count) {
            throw new RuntimeException("재고가 부족합니다.");
        }
        this.itemCount -= count;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                ", imteCount=" + itemCount +
                '}';
    }
}

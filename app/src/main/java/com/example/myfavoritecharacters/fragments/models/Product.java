package com.example.myfavoritecharacters.fragments.models;

public class Product {
    String productName;
    String price;
    String image;
    int quantity;
    public Product( String productName, String price,String image) {
        this.image = image;
        this.productName = productName;
        this.price = price;
    }
    public Product(){

    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
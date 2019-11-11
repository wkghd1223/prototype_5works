package com.example.ex.ui.user.mypage;

import android.graphics.drawable.Drawable;

class ProductList{
    private Drawable ProductImage;
    private String ProductName;
    private String ProductPrice;
    private String ProductInfo;

    public void setProductImage(Drawable productImage) {
        ProductImage = productImage;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public void setProductInfo(String productInfo) {
        ProductInfo = productInfo;
    }

    public Drawable getProductImage() {
        return this.ProductImage;
    }

    public String getProductName() {
        return this.ProductName;
    }

    public String getProductPrice() {
        return this.ProductPrice;
    }

    public String getProductInfo() {
        return this.ProductInfo;
    }
    public ProductList(Drawable productImage, String productName, String productPrice, String productInfo){
        this.setProductImage(productImage);
        this.setProductName(productName);
        this.setProductPrice(productPrice);
        this.setProductInfo(productInfo);
    }
}
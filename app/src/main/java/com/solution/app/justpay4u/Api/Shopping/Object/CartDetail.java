package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartDetail {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName(value = "userID", alternate = "userId")
    @Expose
    public int userID;
    @SerializedName(value = "productID", alternate = "productId")
    @Expose
    public int productID;
    @SerializedName(value = "productDetailID", alternate = "productDetailId")
    @Expose
    public int productDetailID;
    @SerializedName("quantity")
    @Expose
    public int quantity;
    @SerializedName("productCode")
    @Expose
    public String productCode;
    @SerializedName("batch")
    @Expose
    public String batch;
    @SerializedName("mrp")
    @Expose
    public double mrp;
    @SerializedName("sellingPrice")
    @Expose
    public double sellingPrice;
    @SerializedName("discount")
    @Expose
    public double discount;
    @SerializedName("discountType")
    @Expose
    public boolean discountType;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("productName")
    @Expose
    public String productName;
    @SerializedName("imgUrl")
    @Expose
    public String imgUrl;
    @SerializedName("shippingCharges")
    @Expose
    public double shippingCharges;

    public int getId() {
        return id;
    }

    public int getUserID() {
        return userID;
    }

    public int getProductID() {
        return productID;
    }

    public int getProductDetailID() {
        return productDetailID;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getBatch() {
        return batch;
    }

    public double getMrp() {
        return mrp;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public boolean isDiscountType() {
        return discountType;
    }

    public String getDescription() {
        return description;
    }

    public String getProductName() {
        return productName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public double getShippingCharges() {
        return shippingCharges;
    }
}

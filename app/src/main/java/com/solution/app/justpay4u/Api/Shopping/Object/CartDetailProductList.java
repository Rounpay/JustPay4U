package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 19,December,2019
 */
public class CartDetailProductList {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("status")
    @Expose
    public int status;

    @SerializedName("posId")
    @Expose
    public int posId;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("frontImage")
    @Expose
    public String frontImage;
    @SerializedName("smallImage")
    @Expose
    public String smallImage;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("setName")
    @Expose
    public String setName;
    @SerializedName("totalPrice")
    @Expose
    public double totalPrice;
    @SerializedName("shippingCharge")
    @Expose
    public double shippingCharge;
    @SerializedName("totalSum")
    @Expose
    public double totalSum;
    @SerializedName("unitPrice")
    @Expose
    public double unitPrice;
    @SerializedName("discount")
    @Expose
    public double discount;
    @SerializedName("discountType")
    @Expose
    public boolean discountType;
    @SerializedName(value = "quantity", alternate = "qty")
    @Expose
    public int quantity;
    @SerializedName("remainingStock")
    @Expose
    public int remainingStock;
    @SerializedName("cartCount")
    @Expose
    public int cartCount;
    @SerializedName("expectedDate")
    @Expose
    public String expectedDate;
    @SerializedName("mrp")
    @Expose
    public double mrp;

    public String get$id() {
        return $id;
    }

    public int getStatus() {
        return status;
    }


    public int getPosId() {
        return posId;
    }

    public String getMsg() {
        return msg;
    }

    public double getMrp() {
        return mrp;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public String getTitle() {
        return title;
    }

    public String getSetName() {
        return setName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getShippingCharge() {
        return shippingCharge;
    }

    public double getTotalSum() {
        return totalSum;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public boolean isDiscountType() {
        return discountType;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getRemainingStock() {
        return remainingStock;
    }

    public int getCartCount() {
        return cartCount;
    }

    public void setQty(int quantity) {
        this.quantity = quantity;
    }

    public String getExpectedDate() {
        return expectedDate;
    }
}

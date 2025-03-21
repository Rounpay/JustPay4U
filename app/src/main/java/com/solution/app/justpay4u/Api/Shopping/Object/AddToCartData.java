package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 17,December,2019
 */
public class AddToCartData {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("qty")
    @Expose
    public int qty;
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
    @SerializedName("quantity")
    @Expose
    public int quantity;
    @SerializedName("remainingStock")
    @Expose
    public int remainingStock;
    @SerializedName("cartCount")
    @Expose
    public int cartCount;

    public String get$id() {
        return $id;
    }

    public int getStatus() {
        return status;
    }

    public int getQty() {
        return qty;
    }

    public int getPosId() {
        return posId;
    }

    public String getMsg() {
        return msg;
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

    public int getQuantity() {
        return quantity;
    }

    public int getCartCount() {
        return cartCount;
    }

    public int getRemainingStock() {
        return remainingStock;
    }
}

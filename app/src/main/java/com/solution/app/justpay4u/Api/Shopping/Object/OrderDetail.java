package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetail {
    @SerializedName("statusCode")
    @Expose
    public int statusCode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("orderId")
    @Expose
    public int orderId;
    @SerializedName("userId")
    @Expose
    public int userId;
    @SerializedName("quantity")
    @Expose
    public int quantity;
    @SerializedName("totalCost")
    @Expose
    public double totalCost;
    @SerializedName("orderDate")
    @Expose
    public String orderDate;
    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("requestMode")
    @Expose
    public int requestMode;
    @SerializedName("totalShipping")
    @Expose
    public double totalShipping;
    @SerializedName("totalSellingPrice")
    @Expose
    public double totalSellingPrice;
    @SerializedName("totalMRP")
    @Expose
    public double totalMRP;
    @SerializedName("totalDebit")
    @Expose
    public double totalDebit;
    @SerializedName("totalDiscount")
    @Expose
    public double totalDiscount;
    @SerializedName("orderDetailList")
    @Expose
    public List<OrderList> orderDetailList = null;
    @SerializedName("shippingAddress")
    @Expose
    public Address shippingAddress;
    @SerializedName("discountType")
    @Expose
    public boolean discountType;

    public double getTotalDebit() {
        return totalDebit;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public int getStatus() {
        return status;
    }

    public int getRequestMode() {
        return requestMode;
    }

    public double getTotalShipping() {
        return totalShipping;
    }

    public double getTotalMRP() {
        return totalMRP;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public List<OrderList> getOrderDetailList() {
        return orderDetailList;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public double getTotalSellingPrice() {
        return totalSellingPrice;
    }

    public boolean isDiscountType() {
        return discountType;
    }
}

package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 18,December,2019
 */
public class OrderListData implements Serializable {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("orderNo")
    @Expose
    public String orderNo;
    @SerializedName("orderAmount")
    @Expose
    public double orderAmount;
    @SerializedName(value = "totalShippingCharges", alternate = "totalShippingAmount")
    @Expose
    public double totalShippingCharges;
    @SerializedName(value = "totalMRP")
    @Expose
    public double totalMRP;
    @SerializedName("totalDiscount")
    @Expose
    public double totalDiscount;

    @SerializedName("expectedDeliveryDate")
    @Expose
    public String expectedDeliveryDate;

    @SerializedName(value = "transactionID", alternate = "transactionId")
    @Expose
    public String transactionID;

    @SerializedName(value = "gatewayTransactionID", alternate = "gatewayTransactionId")
    @Expose
    public String gatewayTransactionID;
    @SerializedName(value = "gatewayName", alternate = "gateWayName")
    @Expose
    public String gatewayName;

    @SerializedName("gatewayAmount")
    @Expose
    public double gatewayAmount;

    @SerializedName("walletAmount")
    @Expose
    public double walletAmount;

    @SerializedName("totalSellingPrice")
    @Expose
    public double totalSellingPrice;

    @SerializedName(value = "orderedDate", alternate = "orderDate")
    @Expose
    public String orderedDate;
    @SerializedName("list")
    @Expose
    public ArrayList<OrderList> list = null;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public double getTotalShippingCharges() {
        return totalShippingCharges;
    }

    public void setTotalShippingCharges(double totalShippingCharges) {
        this.totalShippingCharges = totalShippingCharges;
    }

    public String getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(String orderedDate) {
        this.orderedDate = orderedDate;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public double getTotalMRP() {
        return totalMRP;
    }

    public void setTotalMRP(double totalMRP) {
        this.totalMRP = totalMRP;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public ArrayList<OrderList> getList() {
        return list;
    }

    public void setList(ArrayList<OrderList> list) {
        this.list = list;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getGatewayTransactionID() {
        return gatewayTransactionID;
    }

    public void setGatewayTransactionID(String gatewayTransactionID) {
        this.gatewayTransactionID = gatewayTransactionID;
    }

    public String getGatewayName() {
        return gatewayName;
    }

    public double getGatewayAmount() {
        return gatewayAmount;
    }

    public void setGatewayAmount(double gatewayAmount) {
        this.gatewayAmount = gatewayAmount;
    }

    public double getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(double walletAmount) {
        this.walletAmount = walletAmount;
    }

    public double getTotalSellingPrice() {
        return totalSellingPrice;
    }

    public void setTotalSellingPrice(double totalSellingPrice) {
        this.totalSellingPrice = totalSellingPrice;
    }
}

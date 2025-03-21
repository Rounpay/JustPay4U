package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CashFreeData {

    @SerializedName("appId")
    @Expose
    private String appId;
    @SerializedName("customerMobile")
    @Expose
    private String customerPhone;
    @SerializedName("customerEmail")
    @Expose
    private String customerEmail;
    @SerializedName("notifyUrl")
    @Expose
    private String notifyUrl;
    @SerializedName("cftoken")
    @Expose
    private String token;
    @SerializedName("orderId")
    @Expose
    private String orderID;
    @SerializedName("orderAmount")
    @Expose
    private Double orderAmount;
    @SerializedName("orderCurrency")
    @Expose
    private String orderCurrency;
    @SerializedName("orderNote")
    @Expose
    private String orderNote;
    @SerializedName("customerName")
    @Expose
    private String customerName;

    @SerializedName("paymentModes")
    @Expose
    private String paymentModes;

    public String getOrderID() {
        return orderID;
    }

    public String getAppId() {
        return appId;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getToken() {
        return token;
    }

    public String getOrderCurrency() {
        return orderCurrency;
    }

    public String getOrderNote() {
        return orderNote;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPaymentModes() {
        return paymentModes;
    }
}

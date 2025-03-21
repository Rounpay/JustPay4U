package com.solution.app.justpay4u.Api.Shopping.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceOrderResponse {


    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("orderId")
    @Expose
    public int orderId;

    @SerializedName("msg")
    @Expose
    public String msg;


    @SerializedName("isVersionValid")
    @Expose
    public boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    public boolean isAppValid;


    public int getStatuscode() {
        return statuscode;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }
}

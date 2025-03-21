package com.solution.app.justpay4u.Api.Shopping.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Shopping.Object.PaymentGatewayData;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 31,December,2019
 */
public class PaymentGatewayResponse {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("status")
    @Expose
    public boolean status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public ArrayList<PaymentGatewayData> data = null;

    public String get$id() {
        return $id;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<PaymentGatewayData> getData() {
        return data;
    }
}

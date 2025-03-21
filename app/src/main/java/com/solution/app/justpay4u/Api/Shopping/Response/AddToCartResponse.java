package com.solution.app.justpay4u.Api.Shopping.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Shopping.Object.AddToCartData;

/**
 * Created by Vishnu Agarwal on 17,December,2019
 */
public class AddToCartResponse {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("status")
    @Expose
    public boolean status;
    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("totalItem")
    @Expose
    public int totalItem;
    @SerializedName(value = "message", alternate = "msg")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public AddToCartData data = null;

    public String get$id() {
        return $id;
    }

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public AddToCartData getData() {
        return data;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public int getTotalItem() {
        return totalItem;
    }
}

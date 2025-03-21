package com.solution.app.justpay4u.Api.Shopping.Response;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Shopping.Object.DeliveryStatus;

import java.util.List;

public class TrackOrderResponse {
    @SerializedName("data")
    @Expose
    private final List<DeliveryStatus> deliveryStatus = null;
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("message")
    @Expose
    private String message;

    public boolean getStatus() {
        return status;
    }


    public String getMessage() {
        if (!TextUtils.isEmpty(message)) {
            return message;
        } else {
            return "";
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DeliveryStatus> getDeliveryStatus() {
        return deliveryStatus;
    }
}

package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 31,December,2019
 */
public class PaymentGatewayData {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("paymentGatewayId")
    @Expose
    public int paymentGatewayId;
    @SerializedName("gatewayName")
    @Expose
    public String gatewayName;
    @SerializedName("displayName")
    @Expose
    public String displayName;
    @SerializedName("gatewayType")
    @Expose
    public String gatewayType;
    @SerializedName("isOTPValidate")
    @Expose
    public boolean isOTPValidate;
    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("msg")
    @Expose
    public String msg;

    public String get$id() {
        return $id;
    }

    public int getPaymentGatewayId() {
        return paymentGatewayId;
    }

    public String getGatewayName() {
        return gatewayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getGatewayType() {
        return gatewayType;
    }

    public boolean getIsOTPValidate() {
        return isOTPValidate;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}

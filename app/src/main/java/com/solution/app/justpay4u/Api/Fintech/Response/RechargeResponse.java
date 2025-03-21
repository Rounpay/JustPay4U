package com.solution.app.justpay4u.Api.Fintech.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RechargeResponse {
    @SerializedName("liveID")
    @Expose
    private String liveID;
    @SerializedName("transactionID")
    @Expose
    private String transactionID;
    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isVersionValid")
    @Expose
    private boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private boolean isAppValid;
    @SerializedName("commAmount")
    @Expose
    private double commAmount;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getLiveID() {
        return liveID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public boolean getIsVersionValid() {
        return isVersionValid;
    }

    public boolean getIsAppValid() {
        return isAppValid;
    }

    public double getCommAmount() {
        return commAmount;
    }
}

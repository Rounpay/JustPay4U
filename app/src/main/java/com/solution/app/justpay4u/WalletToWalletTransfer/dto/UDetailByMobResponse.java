package com.solution.app.justpay4u.WalletToWalletTransfer.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UDetailByMobResponse {

    @SerializedName("uDetailsWTW")
    @Expose
    private UDetailsWTW uDetailsWTW;
    @SerializedName("statuscode")
    @Expose
    private Integer statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isVersionValid")
    @Expose
    private boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private boolean isAppValid;

    public UDetailsWTW getuDetailsWTW() {
        return uDetailsWTW;
    }

    public Integer getStatuscode() {
        return statuscode;
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

package com.solution.app.justpay4u.WalletToWalletTransfer.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UDetailsWTW {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("userID")
    @Expose
    private Integer userID;
    @SerializedName("outletName")
    @Expose
    private String outletName;
    @SerializedName("roleID")
    @Expose
    private Integer roleID;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("isPrepaidB")
    @Expose
    private boolean isPrepaidB;
    @SerializedName("isUtilityB")
    @Expose
    private boolean isUtilityB;
    @SerializedName("isBankB")
    @Expose
    private boolean isBankB;
    @SerializedName("isDoubleFactor")
    @Expose
    private boolean isDoubleFactor;

    public boolean isDoubleFactor() {
        return isDoubleFactor;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getOutletName() {
        return outletName;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public boolean isPrepaidB() {
        return isPrepaidB;
    }

    public boolean isUtilityB() {
        return isUtilityB;
    }

    public boolean isBankB() {
        return isBankB;
    }
}

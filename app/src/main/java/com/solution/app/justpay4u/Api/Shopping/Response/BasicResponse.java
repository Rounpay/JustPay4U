package com.solution.app.justpay4u.Api.Shopping.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasicResponse {
    @SerializedName("data")
    @Expose
    public BasicResponse data;
    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("isVersionValid")
    @Expose
    public boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    public boolean isAppValid;
    @SerializedName("isEmailVerified")
    @Expose
    public boolean isEmailVerified;
    @SerializedName("isSocialAlert")
    @Expose
    public boolean isSocialAlert;
    @SerializedName("totalItem")
    @Expose
    public int totalItem;
    @SerializedName("commRate")
    @Expose
    double commRate;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean getVersionValid() {
        return isVersionValid;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public boolean isSocialAlert() {
        return isSocialAlert;
    }

    public boolean getAppValid() {
        return isAppValid;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public BasicResponse getData() {
        return data;
    }

    public double getCommRate() {
        return commRate;
    }
}

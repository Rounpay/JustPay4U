package com.solution.app.justpay4u.ApiHits;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OpTypeIndustryWiseResponse {

    @SerializedName("data")
    @Expose
    public List<OpTypeDataIndustryWise> data = null;


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

    @SerializedName("isPasswordExpired")
    @Expose
    private boolean isPasswordExpired;
    @SerializedName("isAddMoneyEnable")
    @Expose
    private boolean isAddMoneyEnable;
    @SerializedName("isDMTWithPipe")
    @Expose
    private boolean isDMTWithPipe;
/*    boolean isPaymentGatway;
    boolean isUPI;
    boolean isECollectEnable;*/

    public List<OpTypeDataIndustryWise> getData() {
        return data;
    }

    public int getStatuscode() {
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

    public boolean isPasswordExpired() {
        return isPasswordExpired;
    }

    public boolean isAddMoneyEnable() {
        return isAddMoneyEnable;
    }

    public boolean isDMTWithPipe() {
        return isDMTWithPipe;
    }

 /*   public boolean isPaymentGatway() {
        return isPaymentGatway;
    }

    public boolean isUPI() {
        return isUPI;
    }

    public boolean isECollectEnable() {
        return isECollectEnable;
    }*/
}

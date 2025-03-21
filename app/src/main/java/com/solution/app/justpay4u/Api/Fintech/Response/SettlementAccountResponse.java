package com.solution.app.justpay4u.Api.Fintech.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Object.SettlementAccountData;

import java.util.ArrayList;

public class SettlementAccountResponse {

    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isSattlemntAccountVerify")
    @Expose
    private boolean isSattlemntAccountVerify;
    @SerializedName("isVersionValid")
    @Expose
    private boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private boolean isAppValid;
    @SerializedName("data")
    @Expose
    private ArrayList<SettlementAccountData> data;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isSattlemntAccountVerify() {
        return isSattlemntAccountVerify;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public ArrayList<SettlementAccountData> getData() {
        return data;
    }
}

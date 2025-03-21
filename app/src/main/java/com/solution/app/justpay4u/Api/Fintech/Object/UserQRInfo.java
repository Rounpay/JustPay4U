package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserQRInfo {

    @SerializedName("statuscode")
    @Expose
    int statuscode;
    @SerializedName("msg")
    @Expose
    String msg;
    @SerializedName("bankName")
    @Expose
    String bankName;
    @SerializedName("ifsc")
    @Expose
    String ifsc;
    @SerializedName("virtualAccount")
    @Expose
    String virtualAccount;
    @SerializedName("branch")
    @Expose
    String branch;
    @SerializedName("beneName")
    @Expose
    String beneName;
    @SerializedName("userSDetail")
    @Expose
    UserSmartDetail userSDetail;
    @SerializedName("cashfreeSmartDetail")
    @Expose
    ArrayList<UserSmartDetail> cashfreeSmartDetail;
    @SerializedName("iciciCollectData")
    @Expose
    UserSmartDetail<UserSmartDetail> iciciCollectData;
    @SerializedName("razorpayCollectData")
    @Expose
    UserSmartDetail<UserSmartDetail> razorpayCollectData;
    @SerializedName("cashfreeCollectData")
    @Expose
    UserSmartDetail<ArrayList<UserSmartDetail>> cashfreeCollectData;


    public String getBankName() {
        return bankName;
    }

    public String getIfsc() {
        return ifsc;
    }

    public String getVirtualAccount() {
        return virtualAccount;
    }

    public String getBranch() {
        return branch;
    }

    public String getBeneName() {
        return beneName;
    }

    public UserSmartDetail<UserSmartDetail> getUserSDetail() {
        return userSDetail;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public UserSmartDetail<UserSmartDetail> getIciciCollectData() {
        return iciciCollectData;
    }

    public UserSmartDetail<UserSmartDetail> getRazorpayCollectData() {
        return razorpayCollectData;
    }

    public UserSmartDetail<ArrayList<UserSmartDetail>> getCashfreeCollectData() {
        return cashfreeCollectData;
    }

    public ArrayList<UserSmartDetail> getCashfreeSmartDetail() {
        return cashfreeSmartDetail;
    }
}

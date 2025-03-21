package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FundTransferRequest extends BasicRequest{


    boolean isMarkCredit;
    @SerializedName("securityKey")
    @Expose
    private String securityKey;
    @SerializedName("oType")
    @Expose
    private boolean oType;
    @SerializedName("uid")
    @Expose
    private int uid;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("walletType")
    @Expose
    private int walletType;
    @SerializedName("paymentID")
    @Expose
    private int paymentID;
    @SerializedName("amount")
    @Expose
    private String amount;

    public FundTransferRequest(boolean isMarkCredit, String securityKey, boolean oType, int uid, String remark, int walletType, int paymentID, String amount, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.isMarkCredit = isMarkCredit;
        this.securityKey = securityKey;
        this.oType = oType;
        this.uid = uid;
        this.remark = remark;
        this.walletType = walletType;
        this.paymentID = paymentID;
        this.amount = amount;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
    }
}


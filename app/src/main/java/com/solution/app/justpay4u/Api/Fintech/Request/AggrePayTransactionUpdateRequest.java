package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AggrePayTransactionUpdateRequest extends BasicRequest{
    @SerializedName("tid")
    @Expose
    public String tid;
    @SerializedName("amount")
    @Expose
    public int amount;
    @SerializedName("hash")
    @Expose
    public String hash;



    public AggrePayTransactionUpdateRequest(String tid, int amount, String hash, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.tid = tid;
        this.amount = amount;
        this.hash = hash;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
    }

}

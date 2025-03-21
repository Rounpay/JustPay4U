package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerateOrderForUPIRequest extends BasicRequest{

    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("upiid")
    @Expose
    private String upiid;


    public GenerateOrderForUPIRequest(String amount, String upiid, String userID, String sessionID, String session, String appid, String imei, String regKey, String version, String serialNo, int loginTypeID) {
        this.amount = amount;
        this.upiid = upiid;
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

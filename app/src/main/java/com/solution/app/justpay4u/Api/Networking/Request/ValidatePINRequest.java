package com.solution.app.justpay4u.Api.Networking.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;

public class ValidatePINRequest extends BasicRequest {
    @SerializedName("pin")
    @Expose
    public String pin = "";


    public ValidatePINRequest(String isPin, int loginTypeID, String userID, String session, String sessionID, String appid,
                              String imei, String regKey, String version, String serialNo) {
        this.pin = isPin;
        this.loginTypeID = loginTypeID;
        this.userID = userID;
        this.session = session;
        this.sessionID = sessionID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;


    }
}

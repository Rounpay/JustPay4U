package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogoutRequest extends BasicRequest {
    @SerializedName("sessType")
    @Expose
    private String sessType;

    public LogoutRequest(String sessType, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.sessType = sessType;
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


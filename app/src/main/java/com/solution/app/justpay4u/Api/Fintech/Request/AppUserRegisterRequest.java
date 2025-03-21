package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Object.UserCreate;

public class AppUserRegisterRequest extends BasicRequest {

    @SerializedName("userCreate")
    @Expose
    public UserCreate userCreate;

    String securityKey;

    public AppUserRegisterRequest(String securityKey, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session, UserCreate userCreate) {
        this.securityKey = securityKey;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.userCreate = userCreate;
    }
}

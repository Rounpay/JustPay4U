package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OptionalOperatorRequest extends BasicRequest {
    @SerializedName("oid")
    @Expose
    private int oid;

    public OptionalOperatorRequest(int oid, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.oid = oid;
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


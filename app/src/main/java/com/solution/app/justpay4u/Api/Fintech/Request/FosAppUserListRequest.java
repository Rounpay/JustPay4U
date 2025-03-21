package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FosAppUserListRequest extends BasicRequest {
    @SerializedName("roleID")
    @Expose
    private String roleID;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("mobileno")
    @Expose
    private String mobileno;

    @SerializedName("topRows")
    @Expose
    private int topRows;

    public FosAppUserListRequest(int topRows, String mobileno, String name, String roleID, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.roleID = roleID;
        this.topRows = topRows;
        this.mobileno = mobileno;
        this.name = name;
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


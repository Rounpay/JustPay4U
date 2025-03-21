package com.solution.app.justpay4u.Api.Networking.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestAadhaarOTP {

    @SerializedName("appid")
    @Expose
    private String appid;
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("loginTypeID")
    @Expose
    private String loginTypeID;
    @SerializedName("regKey")
    @Expose
    private String regKey;
    @SerializedName("serialNo")
    @Expose
    private String serialNo;
    @SerializedName("session")
    @Expose
    private String session;
    @SerializedName("sessionID")
    @Expose
    private String sessionID;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("AccountNo")
    @Expose
    private String accountNo;

    public RequestAadhaarOTP(String appId, String imei, String loginTypeID, String regKey, String serialNo, String session, String sessionID, String userID, String version, String accountNo) {
        this.appid = appId;
        this.imei = imei;
        this.loginTypeID = loginTypeID;
        this.regKey = regKey;
        this.serialNo = serialNo;
        this.session = session;
        this.sessionID = sessionID;
        this.userID = userID;
        this.version = version;
        this.accountNo = accountNo;
    }
}

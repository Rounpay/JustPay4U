package com.solution.app.justpay4u.Api.Networking.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AadhaarOtpReq {

    @SerializedName("appid")
    @Expose
    public String appid;
    @SerializedName("imei")
    @Expose
    public String imei;
    @SerializedName("loginTypeID")
    @Expose
    public String loginTypeID;
    @SerializedName("regKey")
    @Expose
    public String regKey;
    @SerializedName("serialNo")
    @Expose
    public String serialNo;
    @SerializedName("session")
    @Expose
    public String session;
    @SerializedName("sessionID")
    @Expose
    public String sessionID;
    @SerializedName("userID")
    @Expose
    public String userID;
    @SerializedName("version")
    @Expose
    public String version;
    @SerializedName("OTP")
    @Expose
    public String otp;
    @SerializedName("AccountNo")
    @Expose
    public String accountNo;
    @SerializedName("ReferenceID")
    @Expose
    public String referenceID;

    public AadhaarOtpReq(String appId, String imei, String loginTypeID, String regKey, String serialNo, String session, String sessionID, String userID, String version, String otp, String accountNo, String referenceID) {
        this.appid = appId;
        this.imei = imei;
        this.loginTypeID = loginTypeID;
        this.regKey = regKey;
        this.serialNo = serialNo;
        this.session = session;
        this.sessionID = sessionID;
        this.userID = userID;
        this.version = version;
        this.otp = otp;
        this.accountNo = accountNo;
        this.referenceID = referenceID;
    }
}

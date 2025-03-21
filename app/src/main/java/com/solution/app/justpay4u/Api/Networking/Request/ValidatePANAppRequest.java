package com.solution.app.justpay4u.Api.Networking.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValidatePANAppRequest {
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

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getLoginTypeID() {
        return loginTypeID;
    }

    public void setLoginTypeID(String loginTypeID) {
        this.loginTypeID = loginTypeID;
    }

    public String getRegKey() {
        return regKey;
    }

    public void setRegKey(String regKey) {
        this.regKey = regKey;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ValidatePANAppRequest(String appid, String imei, String loginTypeID, String regKey, String serialNo, String session, String sessionID, String userID, String version, String accountNo) {
        this.appid = appid;
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

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
}

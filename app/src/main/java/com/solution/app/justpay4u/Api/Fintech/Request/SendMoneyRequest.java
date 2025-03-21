package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendMoneyRequest  extends BasicRequest{
    String oid;
    private RequestSendMoney reqSendMoney;
    @SerializedName("securityKey")
    @Expose
    private String securityKey;

    public SendMoneyRequest(RequestSendMoney reqSendMoney, String securityKey, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.reqSendMoney = reqSendMoney;
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
    }

    public SendMoneyRequest(String oid, RequestSendMoney reqSendMoney, String securityKey, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.oid = oid;
        this.reqSendMoney = reqSendMoney;
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
    }
}

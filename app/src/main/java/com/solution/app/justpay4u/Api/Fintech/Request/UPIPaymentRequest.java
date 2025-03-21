package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Object.SenderObject;


public class UPIPaymentRequest extends BasicRequest {

    @SerializedName(value = "reqSendMoney", alternate = {"appSendMoneyReq"})
    @Expose
    private SendMoneyUPIRequest reqSendMoney;
    private SenderObject senderRequest;
    private String accountNo, securityKey;

    public UPIPaymentRequest(SendMoneyUPIRequest reqSendMoney, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String session, String sessionID) {
        this.reqSendMoney = reqSendMoney;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.session = session;
        this.sessionID = sessionID;
    }

    public UPIPaymentRequest(String securityKey, SendMoneyUPIRequest reqSendMoney, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String session, String sessionID) {
        this.securityKey = securityKey;
        this.reqSendMoney = reqSendMoney;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.session = session;
        this.sessionID = sessionID;
    }

    public UPIPaymentRequest(SenderObject senderRequest, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String session, String sessionID) {
        this.senderRequest = senderRequest;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.session = session;
        this.sessionID = sessionID;
    }

    public UPIPaymentRequest(String accountNo, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String session, String sessionID) {
        this.accountNo = accountNo;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.session = session;
        this.sessionID = sessionID;
    }

}

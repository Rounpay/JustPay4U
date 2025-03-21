package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UPIPaymentReq extends BasicRequest {

    @SerializedName(value = "reqSendMoney", alternate = {"appSendMoneyReq"})
    @Expose
    private ReqSendMoney appSendMoneyReq;
    private String accountNo, securityKey;

    public UPIPaymentReq(ReqSendMoney reqSendMoney, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String session, String sessionID) {
        this.appSendMoneyReq = reqSendMoney;
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

    public UPIPaymentReq(String securityKey, ReqSendMoney reqSendMoney, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String session, String sessionID) {
        this.securityKey = securityKey;
        this.appSendMoneyReq = reqSendMoney;
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

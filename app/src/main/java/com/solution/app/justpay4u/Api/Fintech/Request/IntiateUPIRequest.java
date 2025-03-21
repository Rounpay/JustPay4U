package com.solution.app.justpay4u.Api.Fintech.Request;

public class IntiateUPIRequest extends BasicRequest{


    public String walletID, oid, amount, upiid;


    public IntiateUPIRequest(String walletID, String oid, String amount, String upiid, String userID,
                             int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.walletID = walletID;
        this.oid = oid;
        this.amount = amount;
        this.upiid = upiid;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
    }
}

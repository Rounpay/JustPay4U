package com.solution.app.justpay4u.Api.Fintech.Request;

/**
 * Created by Vishnu Agarwal on 22,November,2019
 */
public class PurchaseTokenRequest extends BasicRequest {
    public String oid;
    public String accountNo;
    public String amount;
    public String outletID;

    public PurchaseTokenRequest(String oid, String accountNo, String amount, String userID, String sessionID, String session, String appid, String imei, String regKey, String version, String serialNo, int loginTypeID, String outletID) {
        this.oid = oid;
        this.accountNo = accountNo;
        this.amount = amount;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
        this.outletID = outletID;
    }
}

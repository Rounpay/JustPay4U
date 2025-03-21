package com.solution.app.justpay4u.Api.Fintech.Request;

/**
 * Created by Vishnu Agarwal on 22,January,2020
 */
public class GatewayTransactionRequest extends BasicRequest{

    String amount;
    String upgid;
    String walletID;
    String oid;

    public GatewayTransactionRequest(String amount, String upgid, String walletID, String oid, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.amount = amount;
        this.upgid = upgid;
        this.walletID = walletID;
        this.oid = oid;
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

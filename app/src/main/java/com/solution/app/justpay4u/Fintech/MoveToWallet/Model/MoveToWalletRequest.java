package com.solution.app.justpay4u.Fintech.MoveToWallet.Model;

public class MoveToWalletRequest {

    String appid;
    String imei;
    int loginTypeID;
    String regKey;
    String serialNo;
    String session;
    String sessionID;
    String userID;
    String version;
    String actionType;
    String TransMode;
    String amount;
    int MTWID;
    int OID;

    public MoveToWalletRequest(String appid, String imei, int loginTypeID, String regKey, String serialNo,
                               String session, String sessionID, String userID, String version, String actionType,
                               String transMode, String amount, int MTWID) {
        this.appid = appid;
        this.imei = imei;
        this.loginTypeID = loginTypeID;
        this.regKey = regKey;
        this.serialNo = serialNo;
        this.session = session;
        this.sessionID = sessionID;
        this.userID = userID;
        this.version = version;
        this.actionType = actionType;
        this.amount = amount;
        this.TransMode = transMode;
        this.MTWID = MTWID;
    }

    public MoveToWalletRequest(String appid, String imei, int loginTypeID, String regKey, String serialNo,
                               String session, String sessionID, String userID, String version, String actionType,
                               String amount, int OID, int MTWID) {
        this.appid = appid;
        this.imei = imei;
        this.loginTypeID = loginTypeID;
        this.regKey = regKey;
        this.serialNo = serialNo;
        this.session = session;
        this.sessionID = sessionID;
        this.userID = userID;
        this.version = version;
        this.actionType = actionType;
        this.amount = amount;
        this.OID = OID;
        this.MTWID = MTWID;
    }
}

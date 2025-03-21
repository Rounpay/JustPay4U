package com.solution.app.justpay4u.Api.Fintech.Request;

public class W2RRequest extends BasicRequest {


    String TID;
    String RPID;
    String RightAccount;

    public W2RRequest(String appid, String imei, int loginTypeID, String regKey, String serialNo, String session, String sessionID, String userID, String version, String TID, String RPID, String rightAccount) {
        this.appid = appid;
        this.imei = imei;
        this.loginTypeID = loginTypeID;
        this.regKey = regKey;
        this.serialNo = serialNo;
        this.session = session;
        this.sessionID = sessionID;
        this.userID = userID;
        this.version = version;
        this.TID = TID;
        this.RPID = RPID;
        RightAccount = rightAccount;
    }

}

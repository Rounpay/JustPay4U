package com.solution.app.justpay4u.Api.Fintech.Request;

public class UpdateUPIRequest  extends BasicRequest{


    String tid;
    String bankStatus;

    public UpdateUPIRequest(String tid, String bankStatus, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.tid = tid;
        this.bankStatus = bankStatus;
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

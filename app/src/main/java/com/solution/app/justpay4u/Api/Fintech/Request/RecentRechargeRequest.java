package com.solution.app.justpay4u.Api.Fintech.Request;

public class RecentRechargeRequest  extends BasicRequest{

    int ServiceID;


    public RecentRechargeRequest(String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session, int ServiceID) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.ServiceID = ServiceID;
    }


}

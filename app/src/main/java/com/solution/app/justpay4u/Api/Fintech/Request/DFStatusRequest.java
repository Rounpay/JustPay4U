package com.solution.app.justpay4u.Api.Fintech.Request;

public class DFStatusRequest extends BasicRequest{
    boolean isDoubleFactor;
    String otp;
    String refID;

    public DFStatusRequest(boolean isDoubleFactor, String otp, String refID, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.isDoubleFactor = isDoubleFactor;
        this.otp = otp;
        this.refID = refID;
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

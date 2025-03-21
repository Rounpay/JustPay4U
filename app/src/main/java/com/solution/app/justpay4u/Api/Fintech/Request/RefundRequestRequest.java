package com.solution.app.justpay4u.Api.Fintech.Request;

public class RefundRequestRequest  extends BasicRequest{
    public String tid;
    public String transactionID;
    public boolean isResend;
    public String otp;

    public RefundRequestRequest(String tid, String transactionID, String userID, String sessionID, String session, String appid, String imei, String regKey, String version, String serialNo, int loginTypeID, String otp, boolean isResend) {
        this.tid = tid;
        this.transactionID = transactionID;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
        this.otp = otp;
        this.isResend = isResend;

    }
}

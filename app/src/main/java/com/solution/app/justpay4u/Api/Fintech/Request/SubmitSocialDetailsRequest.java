package com.solution.app.justpay4u.Api.Fintech.Request;

public class SubmitSocialDetailsRequest  extends BasicRequest{
    String whatsappNo;
    String telegramNo;
    String hangoutId;

    public SubmitSocialDetailsRequest(String whatsappNo, String telegramNo, String hangoutId, String userID, int loginTypeID, String appid,
                                      String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.whatsappNo = whatsappNo;
        this.telegramNo = telegramNo;
        this.hangoutId = hangoutId;
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

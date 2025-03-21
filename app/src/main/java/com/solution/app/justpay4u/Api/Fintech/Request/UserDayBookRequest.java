package com.solution.app.justpay4u.Api.Fintech.Request;

public class UserDayBookRequest extends BasicRequest {

    private String fromDate, toDate;

    private String accountNo;

    public UserDayBookRequest(String fromDate, String toDate, String accountNo, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.accountNo = accountNo;
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


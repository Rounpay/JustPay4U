package com.solution.app.justpay4u.Api.Networking.Request;

import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;

public class GetIncomeWiseReportRequest extends BasicRequest {

    int incomeCategoryID, OPID;
    String FromDate, ToDate, LevelNo;

    public GetIncomeWiseReportRequest(String FromDate, String ToDate, String LevelNo, int incomeCategoryID, int OPID, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.FromDate = FromDate;
        this.ToDate = ToDate;
        this.LevelNo = LevelNo;
        this.incomeCategoryID = incomeCategoryID;
        this.OPID = OPID;
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

package com.solution.app.justpay4u.Fintech.Employee.Api.Request;

public class MeetingDetailRequest {
    String cValue;
    int criteria;
    int top;
    String dtFrom, dtTill;
    public String userID;

    public String sessionID;

    public String session;

    public String appid;

    public String imei;

    public String regKey;

    public String version;

    public String serialNo;

    public int loginTypeID;
    public int searchId;


    public MeetingDetailRequest(int criteria, String cValue, String dtFrom, String dtTill,
                                int top, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.criteria = criteria;
        this.cValue = cValue;
        this.dtTill = dtTill;
        this.dtFrom = dtFrom;
        this.top = top;
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

    public MeetingDetailRequest(int searchId, int criteria, String cValue, String dtFrom, String dtTill,
                                int top, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.searchId = searchId;
        this.criteria = criteria;
        this.cValue = cValue;
        this.dtTill = dtTill;
        this.dtFrom = dtFrom;
        this.top = top;
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

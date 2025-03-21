package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FosAccStmtAndCollReportRequest extends BasicRequest{
    @SerializedName("topRows")
    @Expose
    private String topRows;
    @SerializedName("fromDate")
    @Expose
    private String fromDate;
    @SerializedName("toDate")
    @Expose
    private String toDate;
    @SerializedName("uType")
    @Expose
    private String uType;
    @SerializedName("areaID")
    @Expose
    private int areaID;
    @SerializedName("Mobile")
    @Expose
    private String Mobile;

    public FosAccStmtAndCollReportRequest(String appid, String fromDate, String regKey, String serialNo, String session, String sessionID, String toDate, String topRows, String version
            , String uType, int areaID, String userID, int loginTypeID, String imei) {

        this.appid = appid;
        this.fromDate = fromDate;
        this.regKey = regKey;
        this.serialNo = serialNo;
        this.session = session;
        this.sessionID = sessionID;
        this.toDate = toDate;
        this.topRows = topRows;
        this.version = version;
        this.uType = uType;
        this.areaID = areaID;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.imei = imei;

    }

    public FosAccStmtAndCollReportRequest(String Mobile, String appid, String fromDate, String regKey, String serialNo, String session, String sessionID, String toDate, String topRows, String version
            , String uType, int areaID, String userID, int loginTypeID, String imei) {

        this.Mobile = Mobile;
        this.appid = appid;
        this.fromDate = fromDate;
        this.regKey = regKey;
        this.serialNo = serialNo;
        this.session = session;
        this.sessionID = sessionID;
        this.toDate = toDate;
        this.topRows = topRows;
        this.version = version;
        this.uType = uType;
        this.areaID = areaID;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.imei = imei;

    }
}

package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserClaimsReportRequest extends BasicRequest {

    @SerializedName("toprows")
    @Expose
    public String topRows = "";
    @SerializedName("fromdate")
    @Expose
    public String fromDate = "";
    @SerializedName("todate")
    @Expose
    public String toDate = "";

    @SerializedName("IsRecent")
    @Expose
    private boolean IsRecent;

    public GetUserClaimsReportRequest(boolean IsRecent, String appid, String imei, String regKey, String version, String serialNo, String topRows, String fromDate, String toDate, String userID, String sessionID, String session, int loginTypeID) {
        this.IsRecent = IsRecent;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.topRows = topRows;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.loginTypeID = loginTypeID;
    }


}

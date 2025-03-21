package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DmrRequest extends BasicRequest{
    @SerializedName("topRows")
    @Expose
    private String topRows;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("apiid")
    @Expose
    private String apiid;
    @SerializedName("fromDate")
    @Expose
    private String fromDate;
    @SerializedName("toDate")
    @Expose
    private String toDate;

    @SerializedName("childMobile")
    @Expose
    private String childMobile;
    @SerializedName("transactionID")
    @Expose
    private String transactionID;
    @SerializedName("accountNo")
    @Expose
    private String accountNo;
    @SerializedName("isExport")
    @Expose
    private boolean isExport;
    @SerializedName("IsRecent")
    @Expose
    private boolean IsRecent;

    public DmrRequest(String topRows, int status, String apiid, String fromDate, String toDate, String transactionID, String accountNo, String childMobile, boolean isExport, String userID, String sessionID, String session, String appid, String imei, String regKey, String version, String serialNo, int loginTypeID, boolean IsRecent) {
        this.topRows = topRows;
        this.status = status;
        this.apiid = apiid;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.transactionID = transactionID;
        this.accountNo = accountNo;
        this.childMobile = childMobile;
        this.isExport = isExport;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
        this.IsRecent = IsRecent;
    }
}

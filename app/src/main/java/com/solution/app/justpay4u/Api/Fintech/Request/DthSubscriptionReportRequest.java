package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DthSubscriptionReportRequest extends BasicRequest{

    @SerializedName("topRows")
    @Expose
    public String topRows = "";
    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("bookingStatus")
    @Expose
    public int bookingStatus;
    @SerializedName("fromDate")
    @Expose
    public String fromDate = "";
    @SerializedName("toDate")
    @Expose
    public String toDate = "";
    @SerializedName("transactionID")
    @Expose
    public String transactionID = "";
    @SerializedName("accountNo")
    @Expose
    public String accountNo = "";
    @SerializedName("isExport")
    @Expose
    public String isExport = "";
    @SerializedName("childMobile")
    @Expose
    public String childMobile = "";
    @SerializedName("oid")
    @Expose
    private String oid;
    @SerializedName("opTypeID")
    @Expose
    private String opTypeID;
    @SerializedName("IsRecent")
    @Expose
    private boolean IsRecent;

    public DthSubscriptionReportRequest(boolean IsRecent, String opTypeID, String oid, String appid, String imei, String regKey,
                                        String version, String serialNo, String topRows, int status, int bookingStatus, String fromDate, String toDate,
                                        String transactionID, String accountNo, String childMobile, String isExport, String userID,
                                        String sessionID, String session, int loginTypeID) {
        this.IsRecent = IsRecent;
        this.opTypeID = opTypeID;
        this.oid = oid;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.topRows = topRows;
        this.status = status;
        this.bookingStatus = bookingStatus;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.transactionID = transactionID;
        this.accountNo = accountNo;
        this.childMobile = childMobile;
        this.isExport = isExport;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.loginTypeID = loginTypeID;
    }


}

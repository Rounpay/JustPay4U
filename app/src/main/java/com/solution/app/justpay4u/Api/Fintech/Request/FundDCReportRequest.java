package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FundDCReportRequest extends BasicRequest{

    @SerializedName("isSelf")
    @Expose
    private boolean isSelf;
    @SerializedName("walletTypeID")
    @Expose
    private int walletTypeID;
    @SerializedName("otherUserMob")
    @Expose
    private String otherUserMob;

    @SerializedName("serviceID")
    @Expose
    private int serviceID;
    @SerializedName("fromDate")
    @Expose
    private String fromDate;
    @SerializedName("toDate")
    @Expose
    private String toDate;

    @SerializedName("accountNo")
    @Expose
    private String accountNo;

    public FundDCReportRequest(boolean isSelf, int walletTypeID, int serviceID, String otherUserMob, String fromDate, String toDate, String accountNo, String userID, String sessionID, String session, String appid, String imei, String regKey, String version, String serialNo, int loginTypeID) {
        this.isSelf = isSelf;
        this.walletTypeID = walletTypeID;
        this.serviceID = serviceID;

        this.otherUserMob = otherUserMob;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.accountNo = accountNo;
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

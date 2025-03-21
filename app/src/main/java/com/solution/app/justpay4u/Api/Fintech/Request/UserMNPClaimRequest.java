package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserMNPClaimRequest  extends BasicRequest{

    @SerializedName("oid")
    @Expose
    public String oid = "";
    @SerializedName("mnpmobile")
    @Expose
    public String mnpmobile = "";
    @SerializedName("amount")
    @Expose
    public String amount = "";



    @SerializedName("referenceid")
    @Expose
    private String referenceid;

    public UserMNPClaimRequest(String referenceid, String appid, String imei, String regKey, String version, String serialNo,
                               String oid,  String mnpmobile, String amount,  String userID, String sessionID, String session,
                               int loginTypeID) {
        this.referenceid = referenceid;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.oid = oid;
        this.mnpmobile = mnpmobile;
        this.amount = amount;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.loginTypeID = loginTypeID;
    }


}


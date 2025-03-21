package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserMNPRegistrationRequest  extends BasicRequest{

    @SerializedName("oid")
    @Expose
    private int oid;
    @SerializedName("mnpmobile")
    @Expose
    private String mnpmobile;
    @SerializedName("securityKey")
    @Expose
    private String securityKey;


    public UserMNPRegistrationRequest(int oid, String mnpmobile, String appid, String imei, String regKey, String version, String serialNo,
                                      int loginTypeID, String userID, String sessionID, String session, String securityKey) {
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
        this.oid = oid;
        this.mnpmobile = mnpmobile;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.securityKey = securityKey;
    }


}

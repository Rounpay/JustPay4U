package com.solution.app.justpay4u.WalletToWalletTransfer.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;




public class UDetailByMobRequest extends BasicRequest {

    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;

    public UDetailByMobRequest(String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session, String mobileNo) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.mobileNo = mobileNo;
    }

}

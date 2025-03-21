package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 22,January,2020
 */
public class PayTMTransactionUpdateRequest  extends BasicRequest{


    @SerializedName("paytmCallbackResp")
    @Expose
    public JsonObject paytmCallbackResp;


    public PayTMTransactionUpdateRequest(JsonObject paytmCallbackResp, String userID, int loginTypeID, String appid, String imei, String regKey,
                                         String version, String serialNo, String sessionID, String session) {
        this.paytmCallbackResp = paytmCallbackResp;
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

package com.solution.app.justpay4u.Api.Networking.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;

/**
 * Created by Vishnu Agarwal on 04/02/2022.
 */
public class GetUserNameRequest extends BasicRequest {


    @SerializedName(value = "teamId", alternate = "TeamId")
    @Expose
    public String teamId;


    public GetUserNameRequest(String teamId, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.teamId = teamId;
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

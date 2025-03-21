package com.solution.app.justpay4u.Api.Networking.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;

/**
 * Created by Vishnu Agarwal on 04/02/2022.
 */
public class TopupUserRequest extends BasicRequest {

    @SerializedName(value = "topupUserId", alternate = "TopupUserId")
    @Expose
    public String topupUserId;
    @SerializedName(value = "packageId", alternate = "PackageId")
    @Expose
    public String packageId;
    @SerializedName(value = "walletId", alternate = "WalletId")
    @Expose
    public String walletId;


    public TopupUserRequest(String topupUserId, String packageId, String walletId, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.topupUserId = topupUserId;
        this.packageId = packageId;
        this.walletId = walletId;
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

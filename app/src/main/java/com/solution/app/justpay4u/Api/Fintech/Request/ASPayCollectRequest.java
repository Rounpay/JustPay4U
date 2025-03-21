package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ASPayCollectRequest extends BasicRequest{


    @SerializedName("uid")
    @Expose
    private int uid;
    @SerializedName("remark")
    @Expose
    private String remark;

    @SerializedName("amount")
    @Expose
    private String amount;


    @SerializedName("collectionMode")
    @Expose
    private String collectionMode;

    @SerializedName("bankName")
    @Expose
    private String bankName;

    @SerializedName("utr")
    @Expose
    private String utr;

    public ASPayCollectRequest(String appid, String imei, String regKey, String serialNo
            , String session, String sessionID, String version, int loginTypeID, String userID, int uid, String collectionMode, String amount, String remark, String bankName, String utr) {

        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.serialNo = serialNo;
        this.session = session;
        this.sessionID = sessionID;
        this.version = version;
        this.loginTypeID = loginTypeID;
        this.userID = userID;
        this.uid = uid;
        this.collectionMode = collectionMode;
        this.amount = amount;
        this.remark = remark;
        this.bankName = bankName;
        this.utr = utr;

    }
}
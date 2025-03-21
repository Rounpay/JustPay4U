package com.solution.app.justpay4u.Api.Shopping.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetailRequest {

    @SerializedName(value = "userID", alternate = "userId")
    @Expose
    public String userID;
    @SerializedName(value = "sessionID", alternate = "sessionId")
    @Expose
    public String sessionID;
    @SerializedName("session")
    @Expose
    public String session;
    @SerializedName("appid")
    @Expose
    public String appid;
    @SerializedName("imei")
    @Expose
    public String imei;
    @SerializedName("regKey")
    @Expose
    public String regKey;
    @SerializedName("version")
    @Expose
    public String version;
    @SerializedName("serialNo")
    @Expose
    public String serialNo;
    @SerializedName(value = "loginTypeID", alternate = "loginTypeId")
    @Expose
    public String loginTypeID;
    @SerializedName("orderid")
    @Expose
    public int orderid;


    public OrderDetailRequest(int orderid, String userID, String loginTypeID, String appid, String imei, String regKey,
                              String version, String serialNo, String sessionID, String session) {
        this.orderid = orderid;
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

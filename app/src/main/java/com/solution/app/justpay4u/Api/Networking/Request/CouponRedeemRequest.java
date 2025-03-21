package com.solution.app.justpay4u.Api.Networking.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 04/02/2022.
 */
public class CouponRedeemRequest {
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
    @SerializedName(value = "mobileNo", alternate = "MobileNo")
    @Expose
    public String mobileNo;
    @SerializedName(value = "operatorId", alternate = "OperatorId")
    @Expose
    public String operatorId;
    @SerializedName(value = "couponCode", alternate = "CouponCode")
    @Expose
    public String couponCode;
    @SerializedName(value = "couponId", alternate = "CouponId")
    @Expose
    public int couponId;
    @SerializedName(value = "couponTypeId", alternate = "CouponTypeId")
    @Expose
    public int couponTypeId;
    @SerializedName(value = "currentRedeemStatus", alternate = "CurrentRedeemStatus")
    @Expose
    public int currentRedeemStatus;


    public CouponRedeemRequest(int currentRedeemStatus, int couponId, int couponTypeId, String couponCode, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.couponCode = couponCode;
        this.currentRedeemStatus = currentRedeemStatus;
        this.couponId = couponId;
        this.couponTypeId = couponTypeId;
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

    public CouponRedeemRequest(String mobileNo, String operatorId, String couponCode, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.couponCode = couponCode;
        this.operatorId = operatorId;
        this.mobileNo = mobileNo;
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

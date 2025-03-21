package com.solution.app.justpay4u.WalletToWalletTransfer.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;




public class WalletToWalletFTRequest extends BasicRequest {

    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("walletID")
    @Expose
    private Integer walletID;
    @SerializedName("pin")
    @Expose
    private String pin;

    public WalletToWalletFTRequest(String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session,int uid, String amount, String remark, Integer walletID,String pin) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.amount = amount;
        this.remark = remark;
        this.uid = uid;
        this.walletID = walletID;
        this.pin = pin;
    }
    public WalletToWalletFTRequest(String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session,int uid, String amount, String remark, Integer walletID) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.amount = amount;
        this.remark = remark;
        this.uid = uid;
        this.walletID = walletID;
    }
}

package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BalanceRequest extends BasicRequest{


    String bankID;
    String amount;
    String transactionID;
    String mobileNo;
    String chequeNo;
    String cardNo;
    String accountHolderName;
    String accountNumber;
    int paymentID;
    String SlabID;
    private String walletTypeID;
    private String parentid;
    @SerializedName("oid")
    @Expose
    public int oid;

    public BalanceRequest(int oid, String amount, int ID, String appid, String imei, int loginTypeID, String regKey,String serialNo, String session, String sessionID, String userID, String version) {
        this.oid = oid;
        this.amount = amount;
        this.ID = ID;
        this.appid = appid;
        this.imei = imei;
        this.loginTypeID = loginTypeID;
        this.regKey = regKey;
        this.serialNo = serialNo;
        this.session = session;
        this.sessionID = sessionID;
        this.userID = userID;
        this.version = version;
    }


    public BalanceRequest(String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
    }

    public BalanceRequest(String ParentID, String SlabID, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.parentid = ParentID;
        this.SlabID = SlabID;
        this.sessionID = sessionID;
        this.session = session;
    }


   /* public BalanceRequest(String SlabID, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String blank, String sessionID, String session) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.SlabID = SlabID;
        this.sessionID = sessionID;
        this.session = session;
    }*/

    public BalanceRequest(String bankID, String amount, String transactionID, String mobileNo, String chequeNo, String cardNo, String accountHolderName, String AccountNo, int PaymentModeID, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session, String walletTypeID) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.bankID = bankID;
        this.amount = amount;
        this.transactionID = transactionID;
        this.mobileNo = mobileNo;
        this.chequeNo = chequeNo;
        this.cardNo = cardNo;
        this.accountHolderName = accountHolderName;
        this.accountNumber = AccountNo;
        this.paymentID = PaymentModeID;
        this.sessionID = sessionID;
        this.session = session;
        this.walletTypeID = walletTypeID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getLoginTypeID() {
        return loginTypeID;
    }

    public void setLoginTypeID(int loginTypeID) {
        this.loginTypeID = loginTypeID;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getRegKey() {
        return regKey;
    }

    public void setRegKey(String regKey) {
        this.regKey = regKey;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}

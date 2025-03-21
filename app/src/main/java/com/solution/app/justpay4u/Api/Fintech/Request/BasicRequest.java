package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Networking.Request.DataTypeRequest;

public class BasicRequest {
    @SerializedName("userID")
    @Expose
    public String userID;
    @SerializedName("sessionID")
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
    @SerializedName("loginTypeID")
    @Expose
    public int loginTypeID;
    @SerializedName("appSession")
    @Expose
    public BasicRequest appSession;
    @SerializedName("uid")
    @Expose
    public int uid;
    @SerializedName("request")
    @Expose
    public DataTypeRequest request;
    @SerializedName("ID")
    @Expose
    public int ID;


    public BasicRequest() {
    }

    public BasicRequest(BasicRequest appSession) {
        this.appSession = appSession;
    }

    public BasicRequest(BasicRequest appSession, DataTypeRequest request) {
        this.appSession = appSession;
        this.request = request;
    }


  /*  public BasicRequest(int oid, int amount, int ID, String appid, String imei, int loginTypeID, String regKey,String serialNo, String session, String sessionID, String userID, String version) {
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
    }*/

    public BasicRequest(int ID, String appid, String imei, int loginTypeID,String serialNo, String session, String sessionID, String userID, String version) {
        this.ID = ID;
        this.appid = appid;
        this.imei = imei;
        this.loginTypeID = loginTypeID;
        this.serialNo = serialNo;
        this.session = session;
        this.sessionID = sessionID;
        this.userID = userID;
        this.version = version;
    }

    public BasicRequest(String appid, String imei, int loginTypeID, String serialNo, String session, String sessionID, String userID, String version) {
        this.appid = appid;
        this.imei = imei;
        this.loginTypeID = loginTypeID;
        this.serialNo = serialNo;
        this.session = session;
        this.sessionID = sessionID;
        this.userID = userID;
        this.version = version;
    }

    public BasicRequest(String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
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

    public BasicRequest(int uid, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.uid = uid;
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


    public BasicRequest(String appid, String imei, int loginTypeID, String regKey, String serialNo, String session, String sessionID, String userID, String version) {
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


    public BasicRequest(String appid, String imei, int loginTypeID, String regKey, String serialNo, String session) {
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
}

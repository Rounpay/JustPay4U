package com.solution.app.justpay4u.Api.Fintech.Request;

import com.solution.app.justpay4u.Api.Fintech.Object.BeneDetail;
import com.solution.app.justpay4u.Api.Fintech.Object.SenderObject;

public class GetSenderRequest extends BasicRequest{
    BeneDetail beneDetail;
    String oid, sid, otp;
    private SenderObject senderRequest;
   

    public GetSenderRequest(SenderObject senderRequest, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.senderRequest = senderRequest;
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

    public GetSenderRequest(String oid, SenderObject senderRequest, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.oid = oid;
        this.senderRequest = senderRequest;
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

    public GetSenderRequest(String oid, String sid, SenderObject senderRequest, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.oid = oid;
        this.sid = sid;
        this.senderRequest = senderRequest;
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

    public GetSenderRequest(SenderObject senderRequest, BeneDetail beneDetail, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.senderRequest = senderRequest;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.beneDetail = beneDetail;
    }

    public GetSenderRequest(String oid, SenderObject senderRequest, BeneDetail beneDetail, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.oid = oid;
        this.senderRequest = senderRequest;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.beneDetail = beneDetail;
    }

    public GetSenderRequest(String oid, String sid, String otp, SenderObject senderRequest, BeneDetail beneDetail, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.oid = oid;
        this.sid = sid;
        this.otp = otp;
        this.senderRequest = senderRequest;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.beneDetail = beneDetail;
    }
}

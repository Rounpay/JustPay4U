package com.solution.app.justpay4u.Api.Fintech.Request;


import com.solution.app.justpay4u.Api.Fintech.Object.PidData;

public class GetAepsRequest extends BasicRequest {
    PidData pidData;
    String pidDataXML, aadhar, amount;
    int interfaceType, bankIIN;
    private String bankName;

    private String Lattitude, Longitude;


    public GetAepsRequest(String pidDataXML, String Lattitude, String Longitude, PidData pidData, String aadhar,
                          int interfaceType, int bankIIN, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.pidDataXML = pidDataXML;
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
        this.pidData = pidData;
        this.aadhar = aadhar;
        this.interfaceType = interfaceType;
        this.bankIIN = bankIIN;
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

    public GetAepsRequest(String pidDataXML, String Lattitude, String Longitude, PidData pidData, String aadhar,
                          int interfaceType, int bankIIN, String bankName, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {

        this.pidDataXML = pidDataXML;
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
        this.pidData = pidData;
        this.aadhar = aadhar;
        this.interfaceType = interfaceType;
        this.bankIIN = bankIIN;
        this.bankName = bankName;
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

    public GetAepsRequest(String pidDataXML, String Lattitude, String Longitude, PidData pidData, String aadhar,
                          String amount, int interfaceType, int bankIIN, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.pidDataXML = pidDataXML;
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
        this.pidData = pidData;
        this.aadhar = aadhar;
        this.amount = amount;
        this.interfaceType = interfaceType;
        this.bankIIN = bankIIN;
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
}

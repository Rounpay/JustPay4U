package com.solution.app.justpay4u.Api.Fintech.Request;

/**
 * Created by Vishnu Agarwal on 01/03/2021.
 */
public class GenerateDepositOTPRequest extends BasicRequest{

    String aadhar, amount;
    int interfaceType, bankIIN;
    String reff1;
    String reff2;
    String reff3;
    String otp;
    String Lattitude;
    String Longitude;
    private String  bankName;


    public GenerateDepositOTPRequest(String Lattitude, String Longitude, String reff1, String reff2, String reff3, String otp, String aadhar, String amount, int interfaceType, int bankIIN, String userID,
                                     int loginTypeID,
                                     String appid, String imei, String regKey, String version, String serialNo, String sessionID,
                                     String session) {
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
        this.reff1 = reff1;
        this.reff2 = reff2;
        this.reff3 = reff3;
        this.otp = otp;
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


    public GenerateDepositOTPRequest(String Lattitude, String Longitude, String aadhar, String amount, int interfaceType, int bankIIN, String userID,
                                     int loginTypeID,
                                     String appid, String imei, String regKey, String version, String serialNo, String sessionID,
                                     String session) {
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
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

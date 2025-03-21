package com.solution.app.justpay4u.Api.Fintech.Request;

public class UpdateSettlementAccountRequest extends BasicRequest {

    String AccountOTP;
    int ReferenceID;
    String AccountHolder;
    String AccountNumber;
    int BankID;
    String BankName;
    String ifsc, UTR;
    int UpdateID;

    public UpdateSettlementAccountRequest(String AccountOTP, int ReferenceID, String userID, int loginTypeID, String appid, String imei, String regKey, String version,
                                          String serialNo, String sessionID, String session, String AccountHolder,
                                          String AccountNumber,
                                          int BankID,
                                          String BankName,
                                          int UpdateID, String ifsc) {
        this.AccountOTP = AccountOTP;
        this.ReferenceID = ReferenceID;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.AccountHolder = AccountHolder;
        this.AccountNumber = AccountNumber;
        this.BankID = BankID;
        this.BankName = BankName;
        this.UpdateID = UpdateID;
        this.ifsc = ifsc;
    }

    public UpdateSettlementAccountRequest(String userID, int loginTypeID, String appid, String imei, String regKey, String version,
                                          String serialNo, String sessionID, String session, String AccountHolder,
                                          String AccountNumber,
                                          int BankID,
                                          String BankName,
                                          int UpdateID, String ifsc) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.AccountHolder = AccountHolder;
        this.AccountNumber = AccountNumber;
        this.BankID = BankID;
        this.BankName = BankName;
        this.UpdateID = UpdateID;
        this.ifsc = ifsc;
    }

    public UpdateSettlementAccountRequest(String userID, int loginTypeID, String appid, String imei, String regKey, String version,
                                          String serialNo, String sessionID, String session,
                                          int UpdateID) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.UpdateID = UpdateID;
    }

    public UpdateSettlementAccountRequest(String userID, int loginTypeID, String appid, String imei, String regKey, String version,
                                          String serialNo, String sessionID, String session,
                                          int UpdateID, String UTR) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.UpdateID = UpdateID;
        this.UTR = UTR;
    }
}

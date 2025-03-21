package com.solution.app.justpay4u.Api.Fintech.Request;

public class UpdateMiniBankStatusRequest  extends BasicRequest{
    String accountNo, bankName;
    String tid, vendorID, aPIStatus, remark;

    private String Lattitude;
    private String Longitude;

    public UpdateMiniBankStatusRequest(String Lattitude, String Longitude, String accountNo, String bankName, String tid, String vendorID, String aPIStatus,
                                       String remark, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {

        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
        this.accountNo = accountNo;
        this.bankName = bankName;
        this.tid = tid;
        this.vendorID = vendorID;
        this.aPIStatus = aPIStatus;
        this.remark = remark;
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

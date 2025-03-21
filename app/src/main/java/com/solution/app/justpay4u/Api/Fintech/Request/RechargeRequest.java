package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RechargeRequest  extends BasicRequest{

    int fetchBillID;

    @SerializedName("oid")
    @Expose
    private int oid;
    @SerializedName("accountNo")
    @Expose
    private String accountNo;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("o1")
    @Expose
    private String o1;
    @SerializedName("o2")
    @Expose
    private String o2;
    @SerializedName("o3")
    @Expose
    private String o3;
    @SerializedName("o4")
    @Expose
    private String o4;
    @SerializedName("customerNo")
    @Expose
    private String customerNo;
    @SerializedName("refID")
    @Expose
    private String refID;
    @SerializedName("geoCode")
    @Expose
    private String geoCode;
    @SerializedName("securityKey")
    @Expose
    private String securityKey;
    @SerializedName("isReal")
    @Expose
    private boolean isReal;

    public RechargeRequest(boolean isReal, String appid, String imei, String regKey, String version, String serialNo, int loginTypeID, int oid, String accountNo, String amount, String o1, String o2, String o3,
                           String o4, String customerNo, String refID, int fetchBillID, String geoCode, String userID, String sessionID, String session, String securityKey) {
        this.isReal = isReal;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
        this.oid = oid;
        this.accountNo = accountNo;
        this.amount = amount;
        this.o1 = o1;
        this.o2 = o2;
        this.o3 = o3;
        this.o4 = o4;
        this.customerNo = customerNo;
        this.refID = refID;
        this.fetchBillID = fetchBillID;
        this.geoCode = geoCode;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.securityKey = securityKey;
    }


}

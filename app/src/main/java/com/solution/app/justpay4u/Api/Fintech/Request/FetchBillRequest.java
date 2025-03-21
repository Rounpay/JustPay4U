package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchBillRequest extends BasicRequest{


    @SerializedName("oid")
    @Expose
    public String oid;
    @SerializedName("accountNo")
    @Expose
    public String accountNo;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("outletID")
    @Expose
    public String outletID;
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
    @SerializedName("geoCode")
    @Expose
    private String geoCode;
    @SerializedName("customerNo")
    @Expose
    private String customerNo;

    public FetchBillRequest(String customerNo, String oid, String accountNo, String o1, String o2, String o3,
                            String o4, String geoCode, String amount, String outletID, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.customerNo = customerNo;
        this.oid = oid;
        this.accountNo = accountNo;
        this.o1 = o1;
        this.o2 = o2;
        this.o3 = o3;
        this.o4 = o4;
        this.geoCode = geoCode;
        this.amount = amount;
        this.outletID = outletID;
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
package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ROfferRequest {


    @SerializedName("appid")
    @Expose
    public String appid = "";
    @SerializedName("imei")
    @Expose
    public String imei = "";
    @SerializedName("regKey")
    @Expose
    public String regKey = "";
    @SerializedName("version")
    @Expose
    public String version = "";
    @SerializedName("serialNo")
    @Expose
    public String serialNo = "";
    @SerializedName("oid")
    @Expose
    private String oid;
    @SerializedName("accountNo")
    @Expose
    private String accountNo;

    public ROfferRequest(String oid, String accountNo, String appid, String imei, String regKey, String version, String serialNo) {
        this.oid = oid;
        this.accountNo = accountNo;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
    }
}

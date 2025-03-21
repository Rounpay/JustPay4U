package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlanRequest {
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
    @SerializedName("circleID")
    @Expose
    private String circleID;

    public PlanRequest(String oid, String circleID, String appid, String imei, String regKey, String version, String serialNo) {
        this.oid = oid;
        this.circleID = circleID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
    }

}

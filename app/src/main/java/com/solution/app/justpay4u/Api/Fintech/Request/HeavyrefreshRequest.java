package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HeavyrefreshRequest extends BasicRequest{

    @SerializedName("accountNo")
    @Expose
    public String accountNo = "";
    @SerializedName("oid")
    @Expose
    private String oid;

    public HeavyrefreshRequest(String oid, String accountNo, String appid, String imei, String regKey, String version, String serialNo) {
        this.oid = oid;
        this.accountNo = accountNo;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
    }

}

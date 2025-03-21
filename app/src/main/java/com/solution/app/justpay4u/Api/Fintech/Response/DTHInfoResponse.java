package com.solution.app.justpay4u.Api.Fintech.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Object.DTHInfoData;
import com.solution.app.justpay4u.Api.Fintech.Object.DTHInfoRecords;

public class DTHInfoResponse {
    @SerializedName("statuscode")
    @Expose
    private int statuscode;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("data")
    @Expose
    private DTHInfoData data;
    @SerializedName("dthciData")
    @Expose
    private DTHInfoRecords dthciData;
    @SerializedName("dataPA")
    @Expose
    private DTHInfoData dataPA;
    @SerializedName("isVersionValid")
    @Expose
    private boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private boolean isAppValid;

    public boolean getIsVersionValid() {
        return isVersionValid;
    }

    public boolean getIsAppValid() {
        return isAppValid;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public DTHInfoData getData() {
        return data;
    }

    public DTHInfoRecords getDthciData() {
        return dthciData;
    }

    public DTHInfoData getDataPA() {
        return dataPA;
    }
}

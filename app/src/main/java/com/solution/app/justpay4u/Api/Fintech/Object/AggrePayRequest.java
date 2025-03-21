package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AggrePayRequest {
    @SerializedName("pgType")
    @Expose
    public int pgType;
    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("url")
    @Expose
    public Object url;
    @SerializedName("keyVals")
    @Expose
    public KeyVals keyVals;

    public int getPgType() {
        return pgType;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public Object getUrl() {
        return url;
    }

    public KeyVals getKeyVals() {
        return keyVals;
    }
}

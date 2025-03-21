package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UPIGatewayRequest {
    @SerializedName("pgType")
    @Expose
    private Integer pgType;
    @SerializedName("statuscode")
    @Expose
    private Integer statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("keyVals")
    @Expose
    private KeyVals keyVals;
    @SerializedName("intentString")
    @Expose
    private String intentString;
    @SerializedName("isIntentAllowed")
    @Expose
    private boolean isIntentAllowed;
    @SerializedName("rPayRequest")
    @Expose
    private Object rPayRequest;

    public Integer getPgType() {
        return pgType;
    }

    public Integer getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public String getUrl() {
        return url;
    }

    public KeyVals getKeyVals() {
        return keyVals;
    }

    public String getIntentString() {
        return intentString;
    }

    public boolean isIntentAllowed() {
        return isIntentAllowed;
    }

    public Object getrPayRequest() {
        return rPayRequest;
    }
}



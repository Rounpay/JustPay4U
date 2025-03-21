package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DthHRData {
    @SerializedName("statusCode")
    @Expose
    int statusCode;
    @SerializedName("msg")
    @Expose
    String msg;
    @SerializedName("accountNo")
    @Expose
    String accountNo;
    @SerializedName("operator")
    @Expose
    String operator;
    @SerializedName("response")
    @Expose
    String response;

    public int getStatusCode() {
        return statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getOperator() {
        return operator;
    }

    public String getResponse() {
        return response;
    }
}

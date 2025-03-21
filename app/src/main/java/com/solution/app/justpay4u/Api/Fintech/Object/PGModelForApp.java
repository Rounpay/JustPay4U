package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 22,January,2020
 */
public class PGModelForApp {
    @SerializedName("token")
    @Expose
    public String token;
    int statuscode;
    String msg;
    int pgid;
    String tid;
    String transactionID;
    RequestPTM requestPTM;
    RequestRazorPay rPayRequest;
    @SerializedName("cashFreeResponse")
    @Expose
    CashFreeData cashFreeResponse;
    @SerializedName("upiGatewayRequest")
    @Expose
    UPIGatewayRequest upiGatewayRequest;
    @SerializedName("aggrePayRequest")
    @Expose
    private AggrePayRequest aggrePayRequest;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public int getPgid() {
        return pgid;
    }

    public String getTid() {
        return tid;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public RequestPTM getRequestPTM() {
        return requestPTM;
    }

    public AggrePayRequest getAggrePayRequest() {
        return aggrePayRequest;
    }

    public CashFreeData getCashFreeResponse() {
        return cashFreeResponse;
    }

    public String getToken() {
        return token;
    }

    public UPIGatewayRequest getUpiGatewayRequest() {
        return upiGatewayRequest;
    }

    public RequestRazorPay getrPayRequest() {
        return rPayRequest;
    }
}

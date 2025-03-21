package com.solution.app.justpay4u.Api.Fintech.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayTmSuccessItemResponse {

    @SerializedName("BANKTXNID")
    @Expose
    private String banktxnid;
    @SerializedName("CHECKSUMHASH")
    @Expose
    private String checksumhash;
    @SerializedName("CURRENCY")
    @Expose
    private String currency;
    @SerializedName("GATEWAYNAME")
    @Expose
    private String gatewayname;
    @SerializedName("MID")
    @Expose
    private String mid;
    @SerializedName("ORDERID")
    @Expose
    private String orderid;
    @SerializedName("PAYMENTMODE")
    @Expose
    private String paymentmode;
    @SerializedName("RESPCODE")
    @Expose
    private String respcode;
    @SerializedName("RESPMSG")
    @Expose
    private String respmsg;
    @SerializedName("STATUS")
    @Expose
    private String status;
    @SerializedName("TXNAMOUNT")
    @Expose
    private String txnamount;
    @SerializedName("TXNDATE")
    @Expose
    private String txndate;
    @SerializedName("TXNID")
    @Expose
    private String txnid;

    public String getBanktxnid() {
        return banktxnid;
    }

    public String getChecksumhash() {
        return checksumhash;
    }

    public String getCurrency() {
        return currency;
    }

    public String getGatewayname() {
        return gatewayname;
    }

    public String getMid() {
        return mid;
    }

    public String getOrderid() {
        return orderid;
    }

    public String getPaymentmode() {
        return paymentmode;
    }

    public String getRespcode() {
        return respcode;
    }

    public String getRespmsg() {
        return respmsg;
    }

    public String getStatus() {
        return status;
    }

    public String getTxnamount() {
        return txnamount;
    }

    public String getTxndate() {
        return txndate;
    }

    public String getTxnid() {
        return txnid;
    }
}

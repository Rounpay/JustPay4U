package com.solution.app.justpay4u.Fintech.MoveToWallet.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionMode {

    @SerializedName("transMode")
    @Expose
    private String transMode;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("min")
    @Expose
    private String min;
    @SerializedName("max")
    @Expose
    private String max;
    @SerializedName("charge")
    @Expose
    private String charge;

    public String getTransMode() {
        return transMode;
    }

    public void setTransMode(String transMode) {
        this.transMode = transMode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

}

package com.solution.app.justpay4u.Api.Fintech.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Object.ROfferObject;
import com.solution.app.justpay4u.Api.Fintech.Object.RofferType;

import java.util.ArrayList;

public class RofferResponse  {

    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private RofferType data;
    @SerializedName("rofferData")
    @Expose
    private ArrayList<ROfferObject> rofferData;
    @SerializedName("dataPA")
    @Expose
    private RofferType dataPA;

    @SerializedName("isVersionValid")
    @Expose
    private boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private boolean isAppValid;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public RofferType getData() {
        return data;
    }

    public RofferType getDataPA() {
        return dataPA;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public ArrayList<ROfferObject> getRofferData() {
        return rofferData;
    }

    public boolean isAppValid() {
        return isAppValid;
    }
}

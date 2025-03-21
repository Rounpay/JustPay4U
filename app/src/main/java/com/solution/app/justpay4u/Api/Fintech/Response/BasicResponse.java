package com.solution.app.justpay4u.Api.Fintech.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasicResponse {

    @SerializedName("data")
    @Expose
    public BasicResponse data;
    @SerializedName("popup")
    @Expose
    public String popup;
    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("totalItem")
    @Expose
    public int totalItem;

    @SerializedName(value="referenceID",alternate = {"ReferenceID","referenceId"})
    @Expose
    public int referenceID;
    @SerializedName("cost")
    @Expose
    public String cost;
    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("isEmailVerified")
    @Expose
    public boolean isEmailVerified;
    @SerializedName("isSocialAlert")
    @Expose
    public boolean isSocialAlert;
    @SerializedName("isVersionValid")
    @Expose
    public boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    public boolean isAppValid;
    @SerializedName("commRate")
    @Expose
    double commRate;

    @SerializedName(value = "TotalCommitment", alternate = "totalCommitment")
    @Expose
    double totalCommitment;
    @SerializedName(value = "TotalAchieved", alternate = "totalAchieved")
    @Expose
    double totalAchieved;
    public int getStatuscode() {
        return statuscode;
    }

    public BasicResponse getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public boolean getVersionValid() {
        return isVersionValid;
    }

    public boolean getAppValid() {
        return isAppValid;
    }

    public String getPopup() {
        return popup;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public boolean isSocialAlert() {
        return isSocialAlert;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public double getCommRate() {
        return commRate;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public double getTotalCommitment() {
        return totalCommitment;
    }

    public double getTotalAchieved() {
        return totalAchieved;
    }

    public String getCost() {
        return cost;
    }

    public int getReferenceID() {
        return referenceID;
    }
}

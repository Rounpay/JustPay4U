package com.solution.app.justpay4u.Api.Fintech.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Object.PackageDetails;

import java.util.ArrayList;

public class GetAvailablePackageResponse {
    @SerializedName("pDetail")
    @Expose
    public ArrayList<PackageDetails> packageDetail = null;

    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isVersionValid")
    @Expose
    private boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private boolean isAppValid;
    @SerializedName("checkID")
    @Expose
    private String checkID;
    @SerializedName("isPasswordExpired")
    @Expose
    private boolean isPasswordExpired;


    public ArrayList<PackageDetails> getPackageDetail() {
        return packageDetail;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean getIsVersionValid() {
        return isVersionValid;
    }

    public boolean getIsAppValid() {
        return isAppValid;
    }

    public String getCheckID() {
        return checkID;
    }

    public boolean getIsPasswordExpired() {
        return isPasswordExpired;
    }

}

package com.solution.app.justpay4u.ApiHits;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Networking.Object.MemberListData;

import java.util.ArrayList;

public class MemberListResponse {

    @SerializedName("leftBusiness")
    @Expose
    public double leftBusiness;
    @SerializedName("rightBusiness")
    @Expose
    public double rightBusiness;
    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("isVersionValid")
    @Expose
    public boolean isVersionValid;
    @SerializedName("data")
    @Expose
    public ArrayList<MemberListData> data;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public double getLeftBusiness() {
        return leftBusiness;
    }

    public double getRightBusiness() {
        return rightBusiness;
    }

    public ArrayList<MemberListData> getData() {
        return data;
    }
}

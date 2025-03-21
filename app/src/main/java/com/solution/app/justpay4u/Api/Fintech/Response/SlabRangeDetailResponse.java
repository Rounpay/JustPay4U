package com.solution.app.justpay4u.Api.Fintech.Response;

import com.solution.app.justpay4u.Api.Fintech.Object.SlabRangeDetail;

import java.util.ArrayList;

public class SlabRangeDetailResponse {


    int statuscode;
    boolean isVersionValid;
    boolean isAppValid;
    boolean isPasswordExpired;
    String msg;
    ArrayList<SlabRangeDetail> slabRangeDetail;
    ArrayList<SlabRangeDetail> data;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public ArrayList<SlabRangeDetail> getSlabRangeDetail() {
        return slabRangeDetail;
    }

    public ArrayList<SlabRangeDetail> getData() {
        return data;
    }

    public boolean getVersionValid() {
        return isVersionValid;
    }

    public boolean getAppValid() {
        return isAppValid;
    }

    public boolean getPasswordExpired() {
        return isPasswordExpired;
    }
}

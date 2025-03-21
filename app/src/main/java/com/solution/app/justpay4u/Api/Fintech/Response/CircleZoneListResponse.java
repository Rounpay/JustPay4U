package com.solution.app.justpay4u.Api.Fintech.Response;

import com.solution.app.justpay4u.Api.Fintech.Object.CircleList;

import java.util.ArrayList;

public class CircleZoneListResponse {

    int statuscode;
    String msg;
    boolean isVersionValid;
    boolean isAppValid;


    ArrayList<CircleList> cirlces;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public ArrayList<CircleList> getCirlces() {
        return cirlces;
    }
}

package com.solution.app.justpay4u.Api.Fintech.Response;

import com.solution.app.justpay4u.Api.Fintech.Object.DthChannels;
import com.solution.app.justpay4u.Api.Fintech.Object.DthPackage;

import java.util.ArrayList;

public class GetDthPackageResponse {
    ArrayList<DthPackage> dthPackage;
    ArrayList<DthChannels> dthChannels;
    int statuscode;
    String msg;
    boolean isVersionValid;
    boolean isAppValid;

    boolean isPasswordExpired;

    public ArrayList<DthPackage> getDthPackage() {
        return dthPackage;
    }

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

    public boolean isPasswordExpired() {
        return isPasswordExpired;
    }

    public ArrayList<DthChannels> getDthChannels() {
        return dthChannels;
    }


}

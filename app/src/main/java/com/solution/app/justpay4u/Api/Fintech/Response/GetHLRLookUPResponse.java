package com.solution.app.justpay4u.Api.Fintech.Response;

/**
 * Created by Vishnu Agarwal on 16,November,2019
 */
public class GetHLRLookUPResponse {
    int oid;
    int circleID;
    int statuscode;
    String msg;
    boolean isVersionValid;
    boolean isAppValid;
    int checkID;
    boolean isPasswordExpired;
    String mobileNo;
    String emailID;
    boolean isLookUpFromAPI;

    public int getOid() {
        return oid;
    }

    public int getCircleID() {
        return circleID;
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

    public int getCheckID() {
        return checkID;
    }

    public boolean isPasswordExpired() {
        return isPasswordExpired;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getEmailID() {
        return emailID;
    }

    public boolean isLookUpFromAPI() {
        return isLookUpFromAPI;
    }
}

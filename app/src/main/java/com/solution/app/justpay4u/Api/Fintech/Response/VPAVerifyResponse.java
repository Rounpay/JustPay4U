package com.solution.app.justpay4u.Api.Fintech.Response;

import com.solution.app.justpay4u.Api.Fintech.Object.VPAVerifyData;

/**
 * Created by Vishnu Agarwal on 16/05/2022.
 */
public class VPAVerifyResponse {
    VPAVerifyData data;
    int statuscode;
    String msg;
    boolean isVersionValid;


    public VPAVerifyData getData() {
        return data;
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
}

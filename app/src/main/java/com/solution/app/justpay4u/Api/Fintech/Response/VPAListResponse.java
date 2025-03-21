package com.solution.app.justpay4u.Api.Fintech.Response;

import com.solution.app.justpay4u.Api.Fintech.Object.VPAList;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 16/05/2022.
 */
public class VPAListResponse {
    ArrayList<VPAList> vpaList;
    int statuscode;
    String msg;
    boolean isVersionValid;


    public ArrayList<VPAList> getVpaList() {
        return vpaList;
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

package com.solution.app.justpay4u.Api.Fintech.Response;

import com.solution.app.justpay4u.Api.Fintech.Request.FundRequestToUsers;

import java.util.ArrayList;

public class FundreqToResponse {
    private String statuscode;
    private String msg;
    private String isVersionValid;
    private String isAppValid;
    private ArrayList<FundRequestToUsers> fundRequestToUsers;

    public String getIsVersionValid() {
        return isVersionValid;
    }

    public String getIsAppValid() {
        return isAppValid;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<FundRequestToUsers> getFundRequestToUsers() {
        return fundRequestToUsers;
    }

    public void setFundRequestToUsers(ArrayList<FundRequestToUsers> fundRequestToUsers) {
        this.fundRequestToUsers = fundRequestToUsers;
    }

}

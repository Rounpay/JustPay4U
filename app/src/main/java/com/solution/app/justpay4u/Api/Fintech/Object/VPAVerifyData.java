package com.solution.app.justpay4u.Api.Fintech.Object;

/**
 * Created by Vishnu Agarwal on 17/05/2022.
 */
public class VPAVerifyData {
    int statuscode;
    String msg;
    int errorCode;
    String rpid;
    String liveID;
    String apiRequestID;
    String accountHolder;
    String accountNo;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getRpid() {
        return rpid;
    }

    public String getLiveID() {
        return liveID;
    }

    public String getApiRequestID() {
        return apiRequestID;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public String getAccountNo() {
        return accountNo;
    }
}

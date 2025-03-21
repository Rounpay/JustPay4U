package com.solution.app.justpay4u.Api.Networking.Request;


import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;

public class GetTopupDetailsByUserIdRequest {

    BasicRequest appSession;
    GetTopupDetailsByUserIdRequest Request;
    String strUserID;
    int BussinessEventID;

    public GetTopupDetailsByUserIdRequest(BasicRequest appSession, GetTopupDetailsByUserIdRequest request) {
        this.appSession = appSession;
        this.Request = request;
    }

    public GetTopupDetailsByUserIdRequest(String strUserID, int BussinessEventID) {
        this.strUserID = strUserID;
        this.BussinessEventID = BussinessEventID;
    }
}


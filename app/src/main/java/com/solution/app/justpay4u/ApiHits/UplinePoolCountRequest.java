package com.solution.app.justpay4u.ApiHits;

import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;

public class UplinePoolCountRequest {

    BasicRequest appSession;
    UplinePoolCountRequest request;
    String UserId;
    int MaxLevel;
    String Status;

    public UplinePoolCountRequest(BasicRequest appSession, UplinePoolCountRequest request) {
        this.appSession = appSession;
        this.request = request;
    }

    public UplinePoolCountRequest(String UserId, int LevelNo, String Status) {
       this.UserId=UserId;
       this.MaxLevel=LevelNo;
       this.Status=Status;
    }
}

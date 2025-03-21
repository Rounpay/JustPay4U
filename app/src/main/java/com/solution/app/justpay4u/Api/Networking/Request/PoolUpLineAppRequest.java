package com.solution.app.justpay4u.Api.Networking.Request;

import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;

public class PoolUpLineAppRequest {
    BasicRequest appSession;
    PoolUpLineAppRequest request;
    String LevelNo;

    public PoolUpLineAppRequest(BasicRequest appSession, PoolUpLineAppRequest request) {
        this.appSession = appSession;
        this.request = request;
    }
    public PoolUpLineAppRequest(String LevelNo) {
        this.LevelNo= LevelNo;
    }
}

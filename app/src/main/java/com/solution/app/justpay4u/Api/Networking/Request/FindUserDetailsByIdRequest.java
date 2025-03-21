package com.solution.app.justpay4u.Api.Networking.Request;

import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;

/**
 * Created by Vishnu Agarwal on 17/08/2022.
 */
public class FindUserDetailsByIdRequest {

    BasicRequest appSession;
    String Request;

    public FindUserDetailsByIdRequest(BasicRequest appSession, String request) {
        this.appSession = appSession;
        Request = request;
    }
}

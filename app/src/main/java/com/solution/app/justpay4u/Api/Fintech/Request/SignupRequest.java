package com.solution.app.justpay4u.Api.Fintech.Request;

import com.solution.app.justpay4u.Api.Fintech.Object.UserCreateSignup;

/**
 * Created by Vishnu Agarwal on 20,December,2019
 */
public class SignupRequest {
    String domain;
    int referalID;
    String appid;
    String imei;
    String regKey;
    String version;
    String serialNo;
    String legs;
    UserCreateSignup userCreate;



    public SignupRequest(int referalID, String domain, String appid, String imei, String regKey, String version, String serialNo, String legs, UserCreateSignup userCreate) {
        this.referalID = referalID;
        this.domain = domain;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.legs = legs;
        this.userCreate = userCreate;
    }
}

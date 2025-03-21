package com.solution.app.justpay4u.Api.Networking.Request;

import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;
import com.solution.app.justpay4u.Api.Networking.Object.AddUser;


/**
 * Created by Vishnu Agarwal on 02/02/2022.
 */
public class AddMemberRequest extends BasicRequest {

    public AddUser userCreate;

    public AddMemberRequest(AddUser userCreate, String appid, String imei, String regKey, String version, String serialNo,
                            int loginTypeID, String userID, String sessionID, String session) {
        this.userCreate = userCreate;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
    }
}

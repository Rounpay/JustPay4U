package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Object.EditUser;

public class UpdateUserRequest extends BasicRequest {
    @SerializedName("editUser")
    @Expose
    private EditUser editUser;

    public UpdateUserRequest(String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session, EditUser editUser) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.editUser = editUser;
    }
}

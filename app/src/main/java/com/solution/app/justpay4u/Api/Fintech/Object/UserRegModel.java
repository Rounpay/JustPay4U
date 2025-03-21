package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRegModel {
    @SerializedName("userInfo")
    @Expose
    public UserInfo userInfo;
    @SerializedName("roleSlab")
    @Expose
    public RoleSlab roleSlab;
    @SerializedName("input")
    @Expose
    public String input;
    @SerializedName("isError")
    @Expose
    public boolean isError;
    @SerializedName("token")
    @Expose
    public String token;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public RoleSlab getRoleSlab() {
        return roleSlab;
    }

    public String getInput() {
        return input;
    }

    public boolean getError() {
        return isError;
    }

    public String getToken() {
        return token;
    }
}

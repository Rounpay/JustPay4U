package com.solution.app.justpay4u.Fintech.Employee.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetEmpDownlineUser {
    @SerializedName("prefix")
    @Expose
    public String prefix;
    @SerializedName("userID")
    @Expose
    public int userID;
    @SerializedName("userMobile")
    @Expose
    public String userMobile;
    @SerializedName("userName")
    @Expose
    public String userName;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("attandance")
    @Expose
    public boolean attandance;

    public String getPrefix() {
        return prefix;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getRole() {
        return role;
    }

    public boolean isAttandance() {
        return attandance;
    }
}

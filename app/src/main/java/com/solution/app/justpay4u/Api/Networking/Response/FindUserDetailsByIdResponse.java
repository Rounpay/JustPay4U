package com.solution.app.justpay4u.Api.Networking.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Networking.Object.GetTopupDetailsByUserIdData;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 17/08/2022.
 */
public class FindUserDetailsByIdResponse {
    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName(value = "userId",alternate = "userID")
    @Expose
    public int userId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName(value = "mobile",alternate = "mobileNo")
    @Expose
    public String mobile;
    @SerializedName(value = "emailId",alternate = "emailID")
    @Expose
    public String emailId;
    @SerializedName("data")
    @Expose
    public ArrayList<GetTopupDetailsByUserIdData> data;

    @SerializedName("isVersionValid")
    @Expose
    public boolean isVersionValid;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmailId() {
        return emailId;
    }

    public ArrayList<GetTopupDetailsByUserIdData> getData() {
        return data;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }
}

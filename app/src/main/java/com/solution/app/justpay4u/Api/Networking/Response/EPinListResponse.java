package com.solution.app.justpay4u.Api.Networking.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Networking.Object.EPinList;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 29/08/2022.
 */
public class EPinListResponse {

    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("data")
    @Expose
    public ArrayList<EPinList> data;

    @SerializedName("isVersionValid")
    @Expose
    public boolean isVersionValid;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public ArrayList<EPinList> getData() {
        return data;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }
}

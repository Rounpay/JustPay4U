package com.solution.app.justpay4u.Api.Networking.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Networking.Object.ListCouponTypeCount;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 21/02/2022.
 */
public class CouponTypeCountResponse {
    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("isVersionValid")
    @Expose
    public boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    public boolean isAppValid; @SerializedName("listCouponTypeCount")
    @Expose
    public ArrayList<ListCouponTypeCount> listCouponTypeCount;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public ArrayList<ListCouponTypeCount> getListCouponTypeCount() {
        return listCouponTypeCount;
    }
}

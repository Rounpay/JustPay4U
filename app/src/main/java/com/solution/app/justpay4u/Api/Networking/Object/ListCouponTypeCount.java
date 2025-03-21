package com.solution.app.justpay4u.Api.Networking.Object;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 21/02/2022.
 */
public class ListCouponTypeCount {
    @SerializedName("couponName")
    @Expose
    public String couponName;
    @SerializedName("couponCount")
    @Expose
    public int couponCount;
    @SerializedName("couponTypeId")
    @Expose
    public int couponTypeId;

    public int getCouponTypeId() {
        return couponTypeId;
    }

    public String getCouponName() {
        return couponName;
    }

    public int getCouponCount() {
        return couponCount;
    }
}

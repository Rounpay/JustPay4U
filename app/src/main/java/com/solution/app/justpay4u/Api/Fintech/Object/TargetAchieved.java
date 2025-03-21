package com.solution.app.justpay4u.Api.Fintech.Object;

/**
 * Created by Vishnu Agarwal on 23,December,2019
 */
public class TargetAchieved {
    int sid;
    String service, imgaePath;
    double target, todaySale;
    double targetTillDate;
    boolean isGift;


    public int getSid() {
        return sid;
    }

    public String getService() {
        return service;
    }

    public double getTarget() {
        return target;
    }

    public double getTodaySale() {
        return todaySale;
    }

    public double getTargetTillDate() {
        return targetTillDate;
    }

    public String getImgaePath() {
        return imgaePath;
    }

    public boolean isGift() {
        return isGift;
    }
}

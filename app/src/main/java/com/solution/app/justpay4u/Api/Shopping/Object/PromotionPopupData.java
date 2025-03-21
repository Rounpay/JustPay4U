package com.solution.app.justpay4u.Api.Shopping.Object;

public class PromotionPopupData {
    int id;
    String imageUrl, rechargeImageUrl, taskImageUrl;
    int type, rechargeType, taskType;
    String redirectUrl, rechargeRedirectUrl, taskRedirectUrl;
    int posId, rechargePosId, taskPosId;
    String websiteId;


    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getType() {
        return type;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public int getPosId() {
        return posId;
    }

    public String getWebsiteId() {
        return websiteId;
    }

    public String getRechargeImageUrl() {
        return rechargeImageUrl;
    }

    public String getTaskImageUrl() {
        return taskImageUrl;
    }

    public int getRechargeType() {
        return rechargeType;
    }

    public int getTaskType() {
        return taskType;
    }

    public String getRechargeRedirectUrl() {
        return rechargeRedirectUrl;
    }

    public String getTaskRedirectUrl() {
        return taskRedirectUrl;
    }

    public int getRechargePosId() {
        return rechargePosId;
    }

    public int getTaskPosId() {
        return taskPosId;
    }
}

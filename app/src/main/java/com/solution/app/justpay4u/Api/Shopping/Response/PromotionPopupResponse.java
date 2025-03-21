package com.solution.app.justpay4u.Api.Shopping.Response;


import com.solution.app.justpay4u.Api.Shopping.Object.PromotionPopupData;

public class PromotionPopupResponse {


    boolean status;
    String message;
    PromotionPopupData data;

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public PromotionPopupData getData() {
        return data;
    }
}

package com.solution.app.justpay4u.Api.Shopping.Response;


import com.solution.app.justpay4u.Api.Shopping.Object.FinalSubmitOrderData;

/**
 * Created by Vishnu Agarwal on 15,January,2020
 */
public class FinalSubmitOrderResponse {
    boolean status;
    String message;
    FinalSubmitOrderData data;

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public FinalSubmitOrderData getData() {
        return data;
    }
}
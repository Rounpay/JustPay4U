package com.solution.app.justpay4u.Api.Shopping.Response;


import com.solution.app.justpay4u.Api.Shopping.Object.CheckDeliveryData;

/**
 * Created by Vishnu Agarwal on 10,January,2020
 */
public class CheckDeliveryResponse {
    boolean status;
    String message;
    CheckDeliveryData data;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public CheckDeliveryData getData() {
        return data;
    }
}

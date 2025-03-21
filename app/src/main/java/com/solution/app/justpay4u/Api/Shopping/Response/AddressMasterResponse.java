package com.solution.app.justpay4u.Api.Shopping.Response;


import com.solution.app.justpay4u.Api.Shopping.Object.AddressData;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 20,December,2019
 */
public class AddressMasterResponse {
    boolean status;
    String message;
    ArrayList<AddressData> data;

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<AddressData> getData() {
        return data;
    }
}

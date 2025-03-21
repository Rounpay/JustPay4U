package com.solution.app.justpay4u.Api.Fintech.Response;

import com.solution.app.justpay4u.Api.Fintech.Object.NumberList;

import java.util.ArrayList;

/**
 * Created by Vishnu on 15-02-2017.
 */

public class NumberSeriesListResponse {

    private int statuscode;
    private String msg;
    private String isVersionValid;
    private String isAppValid;
    private ArrayList<NumberList> numSeries;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public String getIsVersionValid() {
        return isVersionValid;
    }

    public String getIsAppValid() {
        return isAppValid;
    }

    public ArrayList<NumberList> getNumSeries() {
        return numSeries;
    }
}

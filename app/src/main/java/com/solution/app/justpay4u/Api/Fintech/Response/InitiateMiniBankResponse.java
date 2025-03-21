package com.solution.app.justpay4u.Api.Fintech.Response;

import com.solution.app.justpay4u.Api.Fintech.Object.MiniBankData;

public class InitiateMiniBankResponse {
    public MiniBankData data;
    private int statuscode;
    private String msg, tid;
    private boolean isVersionValid;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public String getTid() {
        return tid;
    }

    public MiniBankData getData() {
        return data;
    }
}

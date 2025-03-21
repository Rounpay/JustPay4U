package com.solution.app.justpay4u.Api.Networking.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataUpLineReport {
    @SerializedName("listUserPoolDetails")
    @Expose
    private List<ListUserPoolDetail> listUserPoolDetails;
    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("errorCode")
    @Expose
    private int errorCode;

    public List<ListUserPoolDetail> getListUserPoolDetails() {
        return listUserPoolDetails;
    }

    public void setListUserPoolDetails(List<ListUserPoolDetail> listUserPoolDetails) {
        this.listUserPoolDetails = listUserPoolDetails;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

}

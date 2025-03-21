package com.solution.app.justpay4u.Api.Shopping.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Shopping.Object.OtherOfferList;

import java.util.List;

public class OfferListResponse {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("OtherOfferList")
    @Expose
    private List<OtherOfferList> otherOfferList = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<OtherOfferList> getOtherOfferList() {
        return otherOfferList;
    }

    public void setOtherOfferList(List<OtherOfferList> otherOfferList) {
        this.otherOfferList = otherOfferList;
    }
}

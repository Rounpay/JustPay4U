package com.solution.app.justpay4u.Api.Shopping.Object;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OtherOfferList implements Serializable {

    @SerializedName("AffiliateType")
    @Expose
    private String affiliateType;
    @SerializedName("OtherOffer")
    @Expose
    private List<OtherOffer> otherOffer = null;

    public String getAffiliateType() {
        return affiliateType;
    }

    public void setAffiliateType(String affiliateType) {
        this.affiliateType = affiliateType;
    }

    public List<OtherOffer> getOtherOffer() {
        return otherOffer;
    }

    public void setOtherOffer(List<OtherOffer> otherOffer) {
        this.otherOffer = otherOffer;
    }
}

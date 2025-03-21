package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OtherOffer implements Serializable {

    @SerializedName(value = "ID", alternate = {"id", "Id"})
    @Expose
    private int mID;
    @SerializedName(value = "Name", alternate = "name")
    @Expose
    private String name;
    @SerializedName(value = "Image", alternate = "image")
    @Expose
    private String image;
    @SerializedName(value = "Description", alternate = "description")
    @Expose
    private String description;
    @SerializedName(value = "URL", alternate = "url")
    @Expose
    private String URL;
    @SerializedName(value = "OpenType", alternate = "openType")
    @Expose
    private String openType;
    @SerializedName(value = "AffiliateName", alternate = "affiliateName")
    @Expose
    private String affiliateName;
    @SerializedName(value = "AffiliateType", alternate = "affiliateType")
    @Expose
    private String affiliateType;
    @SerializedName("cashbackType")
    @Expose
    private String cashbackType;
    @SerializedName("cashback")
    @Expose
    private String cashback;


    public int getmID() {
        return mID;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getOpenType() {
        return openType;
    }

    public String getURL() {
        return URL;
    }

    public String getAffiliateName() {
        return affiliateName;
    }

    public String getAffiliateType() {
        return affiliateType;
    }

    public String getCashbackType() {
        return cashbackType;
    }

    public String getCashback() {
        if (cashback != null && !cashback.isEmpty()) {
            return cashback;
        } else {
            return "";
        }

    }
}

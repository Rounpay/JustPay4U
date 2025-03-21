package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Banners {
    @SerializedName("resourceUrl")
    @Expose
    public String resourceUrl;
    @SerializedName("siteResourceUrl")
    @Expose
    public String siteResourceUrl;
    @SerializedName("siteFileName")
    @Expose
    public String siteFileName;
    @SerializedName("popupResourceUrl")
    @Expose
    public String popupResourceUrl;
    @SerializedName("popupFileName")
    @Expose
    public String popupFileName;
    @SerializedName("fileName")
    @Expose
    public String fileName;

    public String getResourceUrl() {
        return resourceUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public String getSiteResourceUrl() {
        return siteResourceUrl;
    }

    public String getSiteFileName() {
        return siteFileName;
    }

    public String getPopupResourceUrl() {
        return popupResourceUrl;
    }

    public String getPopupFileName() {
        return popupFileName;
    }
}

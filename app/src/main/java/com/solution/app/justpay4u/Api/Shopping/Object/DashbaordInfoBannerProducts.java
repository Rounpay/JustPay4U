package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 13,December,2019
 */
public class DashbaordInfoBannerProducts {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("bannerImage")
    @Expose
    public String bannerImage;
    @SerializedName("redirectUrl")
    @Expose
    public String redirectUrl;

    public DashbaordInfoBannerProducts(String $id, String bannerImage, String redirectUrl) {
        this.$id = $id;
        this.bannerImage = bannerImage;
        this.redirectUrl = redirectUrl;
    }

    public String get$id() {
        return $id;
    }

    public String getBannerImage() {
        if (bannerImage != null) {
            return bannerImage.replace("190/226", "422/1000");
        }
        return bannerImage;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}

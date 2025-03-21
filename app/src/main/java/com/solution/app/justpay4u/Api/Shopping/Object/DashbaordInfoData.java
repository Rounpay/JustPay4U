package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 13,December,2019
 */
public class DashbaordInfoData {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("menus")
    @Expose
    public ArrayList<DashbaordInfoMenu> menus = null;
    @SerializedName("topcategories")
    @Expose
    public ArrayList<DashbaordInfoTopCategory> topcategories = null;
    @SerializedName("dealofWeek")
    @Expose
    public ArrayList<DashbaordInfoDealOfWeak> dealofWeek = null;
    @SerializedName("websiteSettings")
    @Expose
    public DashbaordInfoWebsiteSettings websiteSettings;
    @SerializedName("bannerProductsFront")
    @Expose
    public ArrayList<DashbaordInfoBannerProducts> bannerProductsFront = null;
    @SerializedName("bannerProductsRight")
    @Expose
    public ArrayList<DashbaordInfoBannerProducts> bannerProductsRight = null;
    @SerializedName("wishListCount")
    @Expose
    public int wishListCount;
    @SerializedName("browserId")
    @Expose
    public String browserId;

    public String get$id() {
        return $id;
    }

    public ArrayList<DashbaordInfoMenu> getMenus() {
        return menus;
    }

    public ArrayList<DashbaordInfoTopCategory> getTopcategories() {
        return topcategories;
    }

    public ArrayList<DashbaordInfoDealOfWeak> getDealofWeek() {
        return dealofWeek;
    }

    public DashbaordInfoWebsiteSettings getWebsiteSettings() {
        return websiteSettings;
    }

    public ArrayList<DashbaordInfoBannerProducts> getBannerProductsFront() {
        return bannerProductsFront;
    }

    public ArrayList<DashbaordInfoBannerProducts> getBannerProductsRight() {
        return bannerProductsRight;
    }

    public int getWishListCount() {
        return wishListCount;
    }

    public String getBrowserId() {
        return browserId;
    }
}

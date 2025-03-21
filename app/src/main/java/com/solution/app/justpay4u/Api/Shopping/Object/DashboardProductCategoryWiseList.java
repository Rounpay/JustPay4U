package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 13,December,2019
 */
public class DashboardProductCategoryWiseList implements Serializable {
    @SerializedName("ViewType")
    @Expose
    private final String viewType;
    @SerializedName("OtherOffer")
    @Expose
    private final ArrayList<OtherOffer> otherOffer = null;
    boolean isAdsNotShow;
    String title;
    int mainCategoryId;
    ArrayList<DashboardProductListData> mDashboardProductListItems = new ArrayList<>();

    public DashboardProductCategoryWiseList(boolean isAdsNotShow, String title, int mainCategoryId, String viewType, ArrayList<DashboardProductListData> mDashboardProductListItems) {
        this.title = title;
        this.isAdsNotShow = isAdsNotShow;
        this.viewType = viewType;
        this.mainCategoryId = mainCategoryId;
        this.mDashboardProductListItems = mDashboardProductListItems;
    }

    public ArrayList<OtherOffer> getOtherOffer() {
        return otherOffer;
    }

    public String getTitle() {
        return title;
    }


    public int getMainCategoryId() {
        return mainCategoryId;
    }

    public ArrayList<DashboardProductListData> getmDashboardProductListItems() {
        return mDashboardProductListItems;
    }

    public boolean isAdsNotShow() {
        return isAdsNotShow;
    }

    public String getViewType() {
        return viewType != null ? viewType : "Horizontal";
    }
}

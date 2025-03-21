package com.solution.app.justpay4u.Api.Shopping.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Shopping.Object.HSliderList;
import com.solution.app.justpay4u.Api.Shopping.Object.LeftMainCategoryList;
import com.solution.app.justpay4u.Api.Shopping.Object.MainCatWiseProduct;

import java.util.ArrayList;

public class AllProductResponse {

    @SerializedName("MainCatWiseProduct")
    @Expose
    private ArrayList<MainCatWiseProduct> mainCatWiseProduct = null;
    @SerializedName("LeftMainCategoryList")
    @Expose
    private ArrayList<LeftMainCategoryList> leftMainCategoryList = null;
    @SerializedName("hSliderList")
    @Expose
    private ArrayList<HSliderList> hSliderList = null;

    public ArrayList<MainCatWiseProduct> getMainCatWiseProduct() {
        return mainCatWiseProduct;
    }

    public void setMainCatWiseProduct(ArrayList<MainCatWiseProduct> mainCatWiseProduct) {
        this.mainCatWiseProduct = mainCatWiseProduct;
    }

    public ArrayList<LeftMainCategoryList> getLeftMainCategoryList() {
        return leftMainCategoryList;
    }

    public void setLeftMainCategoryList(ArrayList<LeftMainCategoryList> leftMainCategoryList) {
        this.leftMainCategoryList = leftMainCategoryList;
    }

    public ArrayList<HSliderList> getHSliderList() {
        return hSliderList;
    }

    public void setHSliderList(ArrayList<HSliderList> hSliderList) {
        this.hSliderList = hSliderList;
    }

}

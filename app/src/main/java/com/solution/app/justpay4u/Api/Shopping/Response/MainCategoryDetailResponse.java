package com.solution.app.justpay4u.Api.Shopping.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Shopping.Object.Filter;
import com.solution.app.justpay4u.Api.Shopping.Object.LeftCategoryList;
import com.solution.app.justpay4u.Api.Shopping.Object.MainCategoryDetail;
import com.solution.app.justpay4u.Api.Shopping.Object.RelatedProduct;

import java.util.ArrayList;
import java.util.List;

public class MainCategoryDetailResponse {

    @SerializedName("Filter")
    @Expose
    public ArrayList<Filter> filter;
    @SerializedName("MainCategoryDetail")
    @Expose
    private MainCategoryDetail mainCategoryDetail;
    @SerializedName("LeftCategoryList")
    @Expose
    private List<LeftCategoryList> leftCategoryList = null;
    @SerializedName("RelatedProduct")
    @Expose
    private List<RelatedProduct> relatedProduct = null;

    public MainCategoryDetail getMainCategoryDetail() {
        return mainCategoryDetail;
    }

    public void setMainCategoryDetail(MainCategoryDetail mainCategoryDetail) {
        this.mainCategoryDetail = mainCategoryDetail;
    }

    public List<LeftCategoryList> getLeftCategoryList() {
        return leftCategoryList;
    }

    public void setLeftCategoryList(List<LeftCategoryList> leftCategoryList) {
        this.leftCategoryList = leftCategoryList;
    }

    public List<RelatedProduct> getRelatedProduct() {
        return relatedProduct;
    }

    public void setRelatedProduct(List<RelatedProduct> relatedProduct) {
        this.relatedProduct = relatedProduct;
    }

    public ArrayList<Filter> getFilter() {
        return filter;
    }

    public void setFilter(ArrayList<Filter> filter) {
        this.filter = filter;
    }

}

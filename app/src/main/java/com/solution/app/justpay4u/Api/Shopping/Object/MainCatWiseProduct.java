package com.solution.app.justpay4u.Api.Shopping.Object;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MainCatWiseProduct {

    @SerializedName("OtherOffer")
    @Expose
    private final ArrayList<OtherOffer> otherOffer = null;
    @SerializedName("MID")
    @Expose
    private int mID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("ViewType")
    @Expose
    private String viewType;
    @SerializedName("ViewBackground")
    @Expose
    private String viewBackground;
    @SerializedName("Categories")
    @Expose
    private ArrayList<LeftCategoryList> categories = null;
    @SerializedName("PList")
    @Expose
    private ArrayList<PList> pList = null;
    private String categoryString = "";


    public int getMID() {
        return mID;
    }

    public void setMID(int mID) {
        this.mID = mID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<LeftCategoryList> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<LeftCategoryList> categories) {
        this.categories = categories;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<PList> getPList() {
        return pList;
    }

    public void setPList(ArrayList<PList> pList) {
        this.pList = pList;
    }

    public String getCategoryString() {
        ArrayList<String> stringArray = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            stringArray.add(categories.get(i).getValue());

        }
        categoryString = TextUtils.join(", ", stringArray);
        return categoryString;
    }

    public void setCategoryString(String categoryString) {
        this.categoryString = categoryString;
    }

    public int getmID() {
        return mID;
    }

    public String getViewType() {
        if (viewType != null && !viewType.isEmpty()) {
            return viewType;
        } else {
            return "Horizontal";
        }
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getViewBackground() {
        return viewBackground;
    }

    public ArrayList<OtherOffer> getOtherOffer() {
        return otherOffer;
    }
}

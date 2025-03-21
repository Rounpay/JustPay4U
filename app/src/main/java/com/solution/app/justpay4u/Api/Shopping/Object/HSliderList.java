package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HSliderList {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("ImagePath1")
    @Expose
    private Object imagePath1;
    @SerializedName("ImagePath")
    @Expose
    private String imagePath;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("IsDefault")
    @Expose
    private Boolean isDefault;
    @SerializedName("EntryDate")
    @Expose
    private String entryDate;
    @SerializedName("ImageUrl")
    @Expose
    private Object imageUrl;
    @SerializedName("HList")
    @Expose
    private Object hList;
    @SerializedName("imagecaption")
    @Expose
    private String imagecaption;
    @SerializedName("MID")
    @Expose
    private Integer mID;
    @SerializedName("CID")
    @Expose
    private Integer cID;
    @SerializedName("MainCatList")
    @Expose
    private Object mainCatList;
    @SerializedName("CatList")
    @Expose
    private Object catList;
    @SerializedName("URL")
    @Expose
    private String uRL;

    public String getuRL() {
        return uRL;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getImagePath1() {
        return imagePath1;
    }

    public void setImagePath1(Object imagePath1) {
        this.imagePath1 = imagePath1;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public Object getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Object imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Object getHList() {
        return hList;
    }

    public void setHList(Object hList) {
        this.hList = hList;
    }

    public String getImagecaption() {
        return imagecaption;
    }

    public void setImagecaption(String imagecaption) {
        this.imagecaption = imagecaption;
    }

    public Integer getMID() {
        return mID;
    }

    public void setMID(Integer mID) {
        this.mID = mID;
    }

    public Integer getCID() {
        return cID;
    }

    public void setCID(Integer cID) {
        this.cID = cID;
    }

    public Object getMainCatList() {
        return mainCatList;
    }

    public void setMainCatList(Object mainCatList) {
        this.mainCatList = mainCatList;
    }

    public Object getCatList() {
        return catList;
    }

    public void setCatList(Object catList) {
        this.catList = catList;
    }

}

package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PList {

    @SerializedName("ShareLink")
    @Expose
    private String shareLink;

    @SerializedName("AffiliateShareLink")
    @Expose
    private String affiliateShareLink;
    @SerializedName("AffiliateWebLink")
    @Expose
    private String affiliateWebLink;
    @SerializedName("LevelWebLink")
    @Expose
    private String levelWebLink;
    @SerializedName("Row")
    @Expose
    private String row;
    @SerializedName("Category")
    @Expose
    private Object category;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("MID")
    @Expose
    private String mID;
    @SerializedName("POSID")
    @Expose
    private String pOSID;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("Price")
    @Expose
    private String price;
    @SerializedName(value = "Discount",alternate = "discount")
    @Expose
    private String discount;
    @SerializedName("DiscountType")
    @Expose
    private String discountType;
    @SerializedName("MRP")
    @Expose
    private String mRP;
    @SerializedName("IsBestSeller")
    @Expose
    private Boolean isBestSeller;
    @SerializedName("IsSpecial")
    @Expose
    private Boolean isSpecial;
    @SerializedName("IsFeatured")
    @Expose
    private Boolean isFeatured;
    @SerializedName("Stock")
    @Expose
    private String stock;
    @SerializedName("WishList")
    @Expose
    private String wishList;


    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public Object getCategory() {
        return category;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMID() {
        return mID;
    }

    public void setMID(String mID) {
        this.mID = mID;
    }

    public String getPOSID() {
        return pOSID;
    }

    public void setPOSID(String pOSID) {
        this.pOSID = pOSID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getMRP() {
        return mRP;
    }

    public void setMRP(String mRP) {
        this.mRP = mRP;
    }

    public Boolean getIsBestSeller() {
        return isBestSeller;
    }

    public void setIsBestSeller(Boolean isBestSeller) {
        this.isBestSeller = isBestSeller;
    }

    public Boolean getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

    public String getAffiliateWebLink() {
        return affiliateWebLink;
    }

    public Boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public String getShareLink() {
        return shareLink;
    }

    public String getAffiliateShareLink() {
        return affiliateShareLink;
    }

    public String getLevelWebLink() {
        return levelWebLink;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getWishList() {
        return wishList;
    }

    public void setWishList(String wishList) {
        this.wishList = wishList;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

}

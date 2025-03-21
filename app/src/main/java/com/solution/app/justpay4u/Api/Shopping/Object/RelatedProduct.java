package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RelatedProduct implements Serializable {

    @SerializedName("Row")
    @Expose
    private Integer row;
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
    @SerializedName("discountType")
    @Expose
    public boolean discountType;
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
    private Integer stock;
    @SerializedName("WishList")
    @Expose
    private String wishList;


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

    public Integer getRow() {
        return row;
    }

    public Object getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getmID() {
        return mID;
    }

    public String getpOSID() {
        if (pOSID != null && !pOSID.isEmpty()) {
            return pOSID.trim();
        } else {
            return "0";
        }
    }

    public String getImage() {
        return image;
    }

    public String getPrice() {
        if (price != null && !price.isEmpty()) {
            return price.replace(".00", "").trim();
        } else {
            return "0";
        }
    }

    public String getDiscount() {
        return discount;
    }

    public boolean isDiscountType() {
        return discountType;
    }

    public String getmRP() {
        return mRP;
    }

    public Boolean getBestSeller() {
        return isBestSeller;
    }

    public Boolean getSpecial() {
        return isSpecial;
    }

    public Boolean getFeatured() {
        return isFeatured;
    }

    public Integer getStock() {
        return stock;
    }

    public String getWishList() {
        return wishList;
    }

    public void setWishList(String wishList) {
        this.wishList = wishList;
    }



    public String getShareLink() {
        return shareLink;
    }

    public String getAffiliateShareLink() {
        return affiliateShareLink;
    }

    public String getAffiliateWebLink() {
        return affiliateWebLink;
    }

    public String getLevelWebLink() {
        return levelWebLink;
    }
}

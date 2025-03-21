package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 13,December,2019
 */
public class DashboardProductListData implements Serializable {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("subCategoryName")
    @Expose
    public String subCategoryName;
    @SerializedName("productName")
    @Expose
    public String productName;
    @SerializedName("setName")
    @Expose
    public String setName;
    @SerializedName("frontImage")
    @Expose
    public String frontImage;
    @SerializedName("smallImage")
    @Expose
    public String smallImage;
    @SerializedName("posId")
    @Expose
    public int posId;
    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("subCategoryId")
    @Expose
    public int subCategoryId;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("remainingQuantity")
    @Expose
    public int remainingQuantity;
    @SerializedName("mrp")
    @Expose
    public double mrp;
    @SerializedName("sellingPrice")
    @Expose
    public double sellingPrice;
    @SerializedName("shippingAmount")
    @Expose
    public double shippingAmount;

    @SerializedName("discount")
    @Expose
    public double discount;
    @SerializedName("discountType")
    @Expose
    public boolean discountType;
    @SerializedName("unitPrice")
    @Expose
    public double unitPrice;

    @SerializedName(value = "mainCategoryId",alternate = "mainCategoryID")
    @Expose
    public int mainCategoryId;
    @SerializedName("productId")
    @Expose
    public int productId;
    @SerializedName("categoryId")
    @Expose
    public int categoryId;
    @SerializedName("mainCategoryName")
    @Expose
    public String mainCategoryName;

    @SerializedName("list")
    @Expose
    public ArrayList<DashboardProductListData> mainCategoryWiseProductList = null;
    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("shareLink")
    @Expose
    private String shareLink;
    @SerializedName("affiliateShareLink")
    @Expose
    private String affiliateShareLink;
    @SerializedName("AffiliateWebLink")
    @Expose
    private String affiliateWebLink;
    @SerializedName("LevelWebLink")
    @Expose
    private String levelWebLink;
    @SerializedName("totalRecords")
    @Expose
    private int totalRecords;
    @SerializedName("isCartAdded")
    @Expose
    private int isCartAdded;
    @SerializedName("qty")
    @Expose
    private int qty;


    public String get$id() {
        return $id;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public double getShippingAmount() {
        return shippingAmount;
    }

    public int getQty() {
        return qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public String getProductName() {
        return productName;
    }

    public String getSetName() {
        return setName;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public int getPosId() {
        return posId;
    }

    public int getStatus() {
        return status;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public String getMsg() {
        return msg;
    }

    public int getIsCartAdded() {
        return isCartAdded;
    }

    public void setIsCartAdded(int isCartAdded) {
        this.isCartAdded = isCartAdded;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public double getMrp() {
        return mrp;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public boolean isDiscountType() {
        return discountType;
    }

    public int getMainCategoryId() {
        return mainCategoryId;
    }

    public String getShareLink() {
        return shareLink;
    }

    public int getProductId() {
        return productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getMainCategoryName() {
        return mainCategoryName;
    }

    public String getTitle() {
        return title;
    }

    public String getAffiliateShareLink() {
        return affiliateShareLink;
    }

    public void setAffiliateShareLink(String affiliateShareLink) {
        this.affiliateShareLink = affiliateShareLink;
    }

    public ArrayList<DashboardProductListData> getMainCategoryWiseProductList() {
        return mainCategoryWiseProductList;
    }

    public String getAffiliateWebLink() {
        return affiliateWebLink;
    }

    public String getLevelWebLink() {
        return levelWebLink;
    }
}

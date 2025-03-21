package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 14,December,2019
 */
public class ProductInfoDetailsData {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("setName")
    @Expose
    public String setName;
    @SerializedName("specificFeatures")
    @Expose
    public ArrayList<ProductInfoSpecificFeature> specificFeatures = null;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("additionalDescription")
    @Expose
    public String additionalDescription;
    @SerializedName("productName")
    @Expose
    public String productName;
    @SerializedName("productCode")
    @Expose
    public String productCode;
    @SerializedName("subCategoryName")
    @Expose
    public String subCategoryName;
    @SerializedName("subCategoryId")
    @Expose
    public int subCategoryId;
    @SerializedName("categoryName")
    @Expose
    public String categoryName;
    @SerializedName(value = "categoryID", alternate = "categoryId")
    @Expose
    public int categoryID;
    @SerializedName("maincategoryName")
    @Expose
    public String maincategoryName;
    @SerializedName(value = "mainCategoryID", alternate = "mainCategoryId")
    @Expose
    public int mainCategoryID;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("frontImage")
    @Expose
    public String frontImage;
    @SerializedName("frontImage_400")
    @Expose
    public String frontImage400;
    @SerializedName("frontImage_1200")
    @Expose
    public String frontImage1200;
    @SerializedName("frontImage_600")
    @Expose
    public String frontImage600;
    @SerializedName("frontImage_50")
    @Expose
    public String frontImage50;
    @SerializedName("frontImage_100")
    @Expose
    public String frontImage100;
    @SerializedName("frontImage_200")
    @Expose
    public String frontImage200;
    @SerializedName("backImage")
    @Expose
    public String backImage;
    @SerializedName("backImage_100")
    @Expose
    public String backImage100;
    @SerializedName("backImage_1200")
    @Expose
    public String backImage1200;
    @SerializedName("backImage_600")
    @Expose
    public String backImage600;
    @SerializedName("backImage_50")
    @Expose
    public String backImage50;
    @SerializedName("backImage_200")
    @Expose
    public String backImage200;
    @SerializedName("backImage_400")
    @Expose
    public String backImage400;
    @SerializedName("sideImage")
    @Expose
    public String sideImage;
    @SerializedName("sideImage_100")
    @Expose
    public String sideImage100;
    @SerializedName("sideImage_1200")
    @Expose
    public String sideImage1200;
    @SerializedName("sideImage_600")
    @Expose
    public String sideImage600;
    @SerializedName("sideImage_50")
    @Expose
    public String sideImage50;
    @SerializedName("sideImage_200")
    @Expose
    public String sideImage200;
    @SerializedName("sideImage_400")
    @Expose
    public String sideImage400;
    @SerializedName("productOptionSetId")
    @Expose
    public int productOptionSetId;
    @SerializedName("mrp")
    @Expose
    public double mrp;
    @SerializedName("shippingCharge")
    @Expose
    public double shippingCharge;
    @SerializedName("expectedDeliveryDate")
    @Expose
    public String expectedDeliveryDate;
    @SerializedName("shareLink")
    @Expose
    public String shareLink;
    @SerializedName("affiliateShareLink")
    @Expose
    public String affiliateShareLink;
    @SerializedName("discount")
    @Expose
    public double discount;
    @SerializedName("discountType")
    @Expose
    public boolean discountType;
    @SerializedName("sellingPrice")
    @Expose
    public double sellingPrice;
    @SerializedName("remainingQuantity")
    @Expose
    public int remainingQuantity;
    @SerializedName("filters")
    @Expose
    public ArrayList<ProductInfoFilter> filters = null;
    @SerializedName("defaultAddress")
    @Expose
    public AddressData addressData;
    @SerializedName("isCartAdded")
    @Expose
    public int isCartAdded;
    @SerializedName("isWishlistAdded")
    @Expose
    public int isWishlistAdded;

    public int getIsCartAdded() {
        return isCartAdded;
    }

    public int getIsWishlistAdded() {
        return isWishlistAdded;
    }

    public String get$id() {
        return $id;
    }

    public String getSetName() {
        return setName;
    }

    public ArrayList<ProductInfoSpecificFeature> getSpecificFeatures() {
        return specificFeatures;
    }

    public String getTitle() {
        return title;
    }

    public String getAdditionalDescription() {
        return additionalDescription;
    }

    public String getProductName() {
        return productName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getMaincategoryName() {
        return maincategoryName;
    }

    public int getMainCategoryID() {
        return mainCategoryID;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getDescription() {
        return description;
    }

    public String getFrontImage400() {
        return frontImage400;
    }

    public String getFrontImage600() {
        return frontImage600;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public String getFrontImage1200() {
        return frontImage1200;
    }

    public String getFrontImage100() {
        return frontImage100;
    }

    public String getBackImage() {
        return backImage;
    }

    public String getBackImage100() {
        return backImage100;
    }

    public String getBackImage1200() {
        return backImage1200;
    }

    public String getSideImage() {
        return sideImage;
    }

    public String getSideImage100() {
        return sideImage100;
    }

    public String getSideImage1200() {
        return sideImage1200;
    }

    public String getFrontImage50() {
        return frontImage50;
    }

    public AddressData getAddressData() {
        return addressData;
    }

    public String getFrontImage200() {
        return frontImage200;
    }

    public String getBackImage600() {
        return backImage600;
    }

    public String getBackImage50() {
        return backImage50;
    }

    public String getBackImage200() {
        return backImage200;
    }

    public String getBackImage400() {
        return backImage400;
    }

    public String getSideImage600() {
        return sideImage600;
    }

    public String getSideImage50() {
        return sideImage50;
    }

    public String getSideImage200() {
        return sideImage200;
    }

    public String getSideImage400() {
        return sideImage400;
    }

    public int getProductOptionSetId() {
        return productOptionSetId;
    }

    public double getMrp() {
        return mrp;
    }

    public double getDiscount() {
        return discount;
    }

    public boolean isDiscountType() {
        return discountType;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public ArrayList<ProductInfoFilter> getFilters() {
        return filters;
    }

    public double getShippingCharge() {
        return shippingCharge;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public String getShareLink() {
        return shareLink;
    }

    public String getAffiliateShareLink() {
        return affiliateShareLink;
    }
}
